# TeamBuilder

Readme.md isn't done yet. I'll handle it after I'm done.

## What is it?

A software application meant to **help** group **psychologists** in building a consistent team. 

This is done through viewing a **social group as a graph**.

Using this perspective, this software application employs several **algorithms** to obtain **hidden information** that can't be seen through normal lens, such as:
  
  - The cliques inside a group, using **k-clustering** algorithms
  - The core of a group, using **k-core decomposition**
  - The consistency of a friend group, using **closeness centrality**
  - The core friend-group inside a group, using **K-core decomposition**
  - The best way someone can meet someone else , using **Djikstra's** distance algorithm
  - The most popular person, using direct **max centrality**
  - The most influential person, using indirect max centrality
  - The least popular person, using direct minimum centrality
  - The least influential person, using indirect minimum centrality

## How does it look like?
Here's a sneak peek of what I'm currently working on. Note that this is **a work in progress** screenshot:
<img width="1534" height="868" alt="image" src="https://github.com/user-attachments/assets/22f55055-804d-42fb-93c5-059971aca88d" />


## What technologies did I use?

For this project I decided to use **pure Java** for the inner workings of this software. 

For the front-end (model), I'm making it using **the Swing library**. 

For the persistence part of this **MVC** software, I'm currently planning on using **PostgreSQL**.



