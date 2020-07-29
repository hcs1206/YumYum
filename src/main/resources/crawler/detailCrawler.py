#!/usr/bin/env python
# coding: utf-8

# In[8]:


from selenium import webdriver

from bs4 import BeautifulSoup
import requests


# In[9]:


import pymysql

import pandas as pd
from pandas import DataFrame, Series

from sqlalchemy import create_engine

# In[10]:


options = webdriver.ChromeOptions()
options.add_argument('headless')
options.add_argument('disable-gpu')

headers = {'User-Agent': (
    'Mozilla/5.0 (Windows NT 10.0;Win64; x64)AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98Safari/537.36')}


# In[11]:


def getDB(dbname):
    engine = create_engine("""mysql+pymysql://root:root@localhost/yumyum?charset=utf8""", encoding="utf8")
    conn = engine.connect()
    data = pd.read_sql_table(dbname, conn)
    conn.close()
    return data


# In[12]:


def createDB(conn):
    curs = conn.cursor()
    query = """CREATE DATABASE yumyum"""
    try:
        curs.execute(query)
    except:
        print('DB가 이미 존재합니다. DB_NAME : yumyum')

    query = """ALTER DATABASE yumyum CHARACTER SET utf8 COLLATE utf8_general_ci;"""
    curs.execute(query)
    conn.commit()


# In[13]:


def save_DB(table_name):
    print("START")
    conn = pymysql.connect(host="localhost", user="root", password="root", charset="utf8")
    createDB(conn)
    curs = conn.cursor()
    curs.execute("""use yumyum""")
    query = """CREATE TABLE IF NOT EXISTS """ + table_name + """(ID varchar(40), phone varchar(20), Doro varchar(50), Jibun varchar(50), 
    time varchar(20), Menu1 varchar(40), cost1 varchar(20), Menu2 varchar(40), cost2 varchar(20), Contents varchar(200));"""
    curs.execute(query)

    query = """ALTER TABLE """ + table_name + """ CHARACTER SET utf8 COLLATE utf8_general_ci;"""
    curs.execute(query)

    conn.commit()
    select_query = """SELECT * from """ + table_name
    index = curs.execute(select_query)

    for row in total_list:
        Id = row[0]
        Phone = row[1]
        doro = row[2]
        jibun = row[3]
        time = row[4]
        menu1 = row[5]
        cost1 = row[6]
        menu2 = row[7]
        cost2 = row[8]
        contents = row[9]

        query = """insert into """ + table_name + """(ID, phone, Doro, Jibun, time, Menu1, cost1, Menu2, cost2, Contents) values (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s) ; """
        curs.execute(query, (Id, Phone, doro, jibun, time, menu1, cost1, menu2, cost2, contents))

        conn.commit()

    curs.close()
    conn.close()
    print("FINISH")


# In[14]:


total_list = []

# In[15]:


keyword = 'm_store'

# In[16]:


stores_df = DataFrame(getDB(keyword))

# In[17]:


for idx, row in stores_df.iterrows():
    url = row['URL']
    res = requests.get(url, headers=headers)
    soup = BeautifulSoup(res.text, 'html.parser')

    try:
        Phone = soup.select_one('#content > div:nth-child(2) > div > div > div.list_item.list_item_biztel > div').string
    except AttributeError as err:
        Phone = ''
    try:
        doro = soup.select_one('#content > div:nth-child(2) > div > div > div.list_item.list_item_address > div > ul > li:nth-child(1) > span').string
    except AttributeError as err:
        doro = ''
    try:
        jibun = soup.select_one('#content > div:nth-child(2) > div > div > div.list_item.list_item_address > div > ul > li:nth-child(2) > span.addr').string
    except AttributeError as err:
        jibun = ''
    try:
        time = soup.select_one('#content > div:nth-child(2) > div > div > div.list_item.list_item_biztime > div > div > div > div > span.time.highlight > span').string
    except AttributeError as err:
        time = ''
    try:
        menu1 = soup.select_one(
            '#content > div:nth-child(2) > div > div > div.list_item.list_item_menu > div > ul > li:nth-child(1) > div > div > div > span').string
    except AttributeError as err:
        menu1 = ''
    try:
        cost1 = soup.select_one(
            '#content > div:nth-child(2) > div > div > div.list_item.list_item_menu > div > ul > li:nth-child(1) > div > em').string
    except AttributeError as err:
        cost1 = ''
    try:
        menu2 = soup.select_one(
            '#content > div:nth-child(2) > div > div > div.list_item.list_item_menu > div > ul > li:nth-child(2) > div > div > div > span').string
    except AttributeError as err:
        menu2 = ''
    try:
        cost2 = soup.select_one(
            '#content > div:nth-child(2) > div > div > div.list_item.list_item_menu > div > ul > li:nth-child(2) > div > em').string
    except AttributeError as err:
        cost2 = ''
    try:
        contents = soup.select_one(
            '#content > div:nth-child(2) > div.bizinfo_area > div > div.list_item.list_item_desc > div > div > span').string
    except AttributeError as err:
        contents= ''

    total_list.append([row['id'],Phone, doro, jibun, time, menu1, cost1, menu2, cost2, contents])

# In[18]:

save_DB('detail')