#!/usr/bin/env python
# coding: utf-8

# In[8]:


from selenium import webdriver
from selenium.webdriver.common.keys import Keys

from bs4 import BeautifulSoup
import requests
import datetime 
import re


# In[9]:


import pymysql

import pandas as pd
from pandas import DataFrame, Series

from sqlalchemy import create_engine


# In[10]:


options = webdriver.ChromeOptions()
options.add_argument('headless')
options.add_argument('disable-gpu')

headers = {'User-Agent' : ('Mozilla/5.0 (Windows NT 10.0;Win64; x64)AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98Safari/537.36')} 


# In[11]:


def getDB(dbname):
    engine = create_engine( """mysql+pymysql://root:root@localhost/NAVER_STORE?charset=utf8""", encoding = "utf8")
    conn = engine.connect()
    data = pd.read_sql_table(dbname, conn)
    conn.close()
    return data


# In[12]:


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


# In[13]:


def saveDB(table_name) : 
    print("START")
    conn = pymysql.connect(host = "localhost", user = "root", password = "root", charset = "utf8")
    createDB(conn)
    curs = conn.cursor()
    curs.execute("""use NAVER_STORE""")
    query = """CREATE TABLE IF NOT EXISTS """+ table_name + """(ID varchar(40), UserName varchar(100),Date varchar(20), Score varchar(5), VisitCount int);"""
    curs.execute(query)

    query = """ALTER TABLE """ + table_name +""" CHARACTER SET utf8 COLLATE utf8_general_ci;"""
    curs.execute(query)

    conn.commit()
    select_query = """SELECT * from """ + table_name
    index = curs.execute(select_query)

    for row in total_list :
        Id = row[0]
        user_name = row[1]
        date = row[2]
        score = row[3]
        visit_count = row[4]

        query = """insert into """ + table_name + """(ID, UserName, Date, Score, VisitCount) values (%s, %s, %s, %s, %s) ; """
        curs.execute(query, (Id, user_name, date, score, visit_count))

        conn.commit()
    
    curs.close()
    conn.close()
    print("FINISH")


# In[14]:


total_list = []


# In[ ]:


keyword = input("Keyword ? ")


# In[15]:


stores_df = DataFrame(getDB("store_"+keyword))


# In[16]:


for idx,row in stores_df.iterrows():
    print(row['ID'])
    print(row['ReviewCount'])
    page_num = int(row['ReviewCount']/10)
    while page_num >= 0 :
        print(page_num)
        url = row['URL'] + str(page_num)
        res = requests.get(url, headers=headers)
        soup = BeautifulSoup(res.text, 'html.parser')

        li_tags = soup.select('ul.list_receipt_review li')

        for li in li_tags :
            score = li.find("span", {"class" : "score"}).getText()
            div_tag = li.find("div", {"class" : "reviewer"})
            user_name = div_tag.find("a").getText()
            span_tags = div_tag.findAll("span")
            date = span_tags[0].getText()
            visit_count = re.findall("\d+",span_tags[1].getText())[0]
            total_list.append([row['ID'], user_name, date, score, visit_count])
            
        page_num-=1


# In[18]:


total_list


# In[19]:


save_DB('review_'+keyword)


# In[ ]:




