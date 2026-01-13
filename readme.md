# TeamBuilder

## What is it?

A software application meant to **help** in keeping track of **teams' structures** and finding ways to make **said teams better**.

## Live demo

[Screencast from 2025-12-06 15-02-39.webm](https://github.com/user-attachments/assets/f8491ece-f9ea-4e3b-9a6f-3ffea0d222a9)

## How was it done?

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

## What technologies did I use?

For this project I decided to use mostly **pure Java** for the inner workings of this software. 

For the front-end, I used **the Swing library** from inside the standard JDK. 

For the persistence part of this software, I've used a **PostgresSQL** instance containerized with **Docker**.

After all this, I tied it all up using **Maven**.


## Other capabilities

### 1. Live information checking directly from the graph

The user can click on the graphical interface and obtain instant information about the target ( be it a relation, or a person).

[Screencast from 2025-12-08 20-37-08.webm](https://github.com/user-attachments/assets/8a456e6f-6c64-4b9b-ae6d-efdafc4e9589)

### 2. Live graphical update

When the user updates the information about the active group, the visual information is dynamically updated.

[Screencast from 2025-12-08 20-41-26.webm](https://github.com/user-attachments/assets/7af64e4a-e332-4518-bda7-b1556301fec8)

### And more! 
Plugging a video with each feature in would make this readme too long and I wouldn't dare to steal your curiosity to find out more.

## Intended behaviour 

<img width="621" height="1531" alt="SoftwareBehaviour" src="https://github.com/user-attachments/assets/691fd857-4d2e-4ee4-8332-4b643519029a" />

## UML Diagram

![UMLDiagram](https://github.com/user-attachments/assets/11ed1b13-511c-4960-a40c-9966e6f367b6)




