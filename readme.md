# TeamBuilder

## What is it?

A software application meant to **help** in keeping track of **teams' structures** and finding ways to make **said teams better**.

This is done through viewing a **social group as a graph**. Each **person** is a **node** and each **bond** is an **edge**.

Using this perspective, this software application employs several **algorithms** to obtain **hidden information** that can't be seen through normal lens, such as:
  
  - The cliques inside a group, using the **weighted clustering** algorithm
  - The core of a group, using **K-core decomposition**
  - The consistency of a friend group, using **Graph-density**
  - The best way someone can meet someone else , using **Dijkstra's** distance algorithm
  - The most popular person, using **direct max centrality**
  - The most influential person, using **indirect max centrality**
  - The least popular person, using **direct minimum centrality**
  - The least influential person, using **indirect minimum centrality**
  - The best partitions for the current group, using **Greedy farthest-point clustering**

## Live demo

[Screencast from 2025-12-06 15-02-39.webm](https://github.com/user-attachments/assets/f8491ece-f9ea-4e3b-9a6f-3ffea0d222a9)


## What technologies did I use?

For this project I decided to use mostly **pure Java** for the inner workings of this software. 

For the front-end, I used **the Swing library** from inside the standard JDK. 

For the persistence part of this software, I've used a **PostgresSQL** instance containerized with **Docker**.

After all this, I tied it all up using **Maven**.




