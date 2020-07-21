#!/usr/bin/env python
# coding: utf-8

# In[1]:


from selenium import webdriver
from selenium.webdriver.common.keys import Keys

from bs4 import BeautifulSoup

import requests
import datetime 
import pymysql
import re
import time


# In[2]:


options = webdriver.ChromeOptions()
options.add_argument('headless')
options.add_argument('disable-gpu')

headers = {'User-Agent' : ('Mozilla/5.0 (Windows NT 10.0;Win64; x64)AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98Safari/537.36')} 


# In[3]:


def createDB(conn):
    curs = conn.cursor()
    query = """CREATE DATABASE NAVER_STORE"""
    try :
        curs.execute(query)
    except :
        print('DB가 이미 존재합니다. DB_NAME : NAVER_STORE')
    
    query = """ALTER DATABASE NAVER_STORE CHARACTER SET utf8 COLLATE utf8_general_ci;"""
    curs.execute(query)
    conn.commit()


# In[4]:


def save_DB(table_name) : 
    print("START")
    conn = pymysql.connect(host = "localhost", user = "root", password = "root", charset = "utf8")
    createDB(conn)
    curs = conn.cursor()
    curs.execute("""use NAVER_STORE""")

    query = """CREATE TABLE IF NOT EXISTS """+ table_name + """(ID varchar(40), NAME varchar(100), URL varchar(100), Address varchar(200), ReviewCount int);"""
    curs.execute(query)

    query = """ALTER TABLE """ + table_name +""" CHARACTER SET utf8 COLLATE utf8_general_ci;"""
    curs.execute(query)

    conn.commit()
    
    select_query = """SELECT * from """ + table_name
    index = curs.execute(select_query)

    for row in total_list :
        Id = row[0]
        name = row[1]
        url = row[2]
        addr = row[3]
        review_count = row[4]

        query = """insert into """ + table_name + """(ID, URL,Name, Address, ReviewCount) values (%s, %s, %s, %s, %s) ; """
        curs.execute(query, (Id, name, url, addr, review_count))

        conn.commit()
        
    conn.close()
    print("FINISH")


# In[15]:


def getTotalCount(store_url) :    
    res = requests.get(store_url, headers=headers)
    soup = BeautifulSoup(res.text, 'html.parser')
    print(store_url)
    
    if soup.find("span", {"class" : "addr"}) is not None :
        store_addr = soup.find("span", {"class" : "addr"}).getText()
    else :
        store_addr = ""
        
    if soup.find("span", {"class" : "count"}) is not None :
        return store_addr, int(soup.find("span", {"class" : "count"}).getText())
    else :
        return store_addr,0


# In[16]:


keyword = input("Keyword ? ")


# In[49]:


total_list = []


# In[50]:


page_num = 4

while page_num <= 4 :
    url = 'https://store.naver.com/restaurants/list?page='+str(page_num)+'&query='+keyword+'&sessionid=2K%2BbSzUXWifHYIDs0aMRG6QJ&sortingOrder=reviewCount'
    print(url)
    driver = webdriver.Chrome('./chromedriver/chromedriver', options = options)
    driver.implicitly_wait(5)
    driver.get(url)
    soup = BeautifulSoup(driver.page_source,'html.parser')
    li_tags = soup.select('ul.list_place_col1 li')

    # 한 페이지에 있는 행들 전부 가져오기
    for li in li_tags :
        store_id = li.attrs['id']
        store_id = re.findall("\d+", store_id)
        store_url = 'https://store.naver.com/restaurants/detail?entry=pll&id='+store_id[0]+'&tab=receiptReview&tabPage='
        store_name = li.find("span", {"class" : "tit_inner"}).find("span").getText()
        store_addr, review_count = getTotalCount(store_url)
        total_list.append([store_id,store_url,store_name, store_addr, review_count])
        
    page_num += 1


# In[51]:


print(total_list)


# In[52]:


save_DB('store_'+keyword)


# In[ ]:




