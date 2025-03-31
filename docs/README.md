# Eddie Task Manager!
```
 _____  ____   ____   ___  _____
| ____||  _ \ |  _ \ |_ _|| ____|
| |_   | | | || | | | | | | |_
|  _|  | | | || | | | | | | |_|   
| |___ | |_| || |_| | | | | |___  
|_____||____/ |____/ |___||_____|

Eddie: Welcome to your personal task manager!
I can help you manage different types of tasks.
```

# Eddie User Guide

## Introduction

**Eddie** is a command-line based chatbot designed to help you organize your tasks. Whether it’s a **Todo**, a **Deadline**, or an **Event** Eddie has you covered! Eddie understands natural-like commands and can **add, list, delete, mark, unmark, find** and even **remember your tasks between sessions** with **date parsing** and **search** features!

---
#  Features
## Adding Tasks

### Add ToDo, Deadline, or Event Tasks

- `todo {description}` – Adds a ToDo task
- `deadline {description} /by {yyyy-MM-dd HHmm}` – Adds a Deadline
- `event {description} /from {yyyy-MM-dd HHmm} /to {yyyy-MM-dd HHmm}` – Adds an Event


Example: adding tasks
```
____________________________________________________________
You: todo read book
____________________________________________________________
Eddie:
Got it. I've added this task:
  [T][ ] read book
Now you have 1 task in the list.
____________________________________________________________
```
```
____________________________________________________________
You: deadline return book /by 2025-04-01 1800
____________________________________________________________
Eddie:
Got it. I've added this task:
  [D][ ] return book (by: Apr 1 2025, 6:00pm)
Now you have 2 tasks in the list.
____________________________________________________________
```
```
__________________________________________________________________________________
You: event group project meeting /from 2025-04-02 1400 /to 2025-04-02 1600
__________________________________________________________________________________
Eddie:
Got it. I've added this task:
  [E][ ] group project meeting (from: Apr 2 2025, 2:00pm to: Apr 2 2025, 4:00pm)
Now you have 3 tasks in the list.
__________________________________________________________________________________
```

---

### List All Tasks

- `list` – Shows all tasks currently tracked.

#### Example:
```
__________________________________________________________________________________
You: list
__________________________________________________________________________________
Eddie:
Here are the tasks in your list:
1. [T][ ] read book
2. [D][ ] return book (by: Apr 1 2025, 6:00pm)
3. [E][ ] group project meeting (from: Apr 2 2025, 2:00pm to: Apr 2 2025, 4:00pm)
__________________________________________________________________________________
```

---

### Mark/Unmark Tasks

- `mark {task number}` – Mark a task as done
- `unmark {task number}` – Mark a task as not done

#### Example:
```
____________________________________________________________
You: mark 1
____________________________________________________________
Eddie:
Oh no! Marked as not done:
  [T][ ] read book
____________________________________________________________
You: unmark 1
____________________________________________________________
Eddie:
Oh no! Marked as not done:
  [T][ ] read book
____________________________________________________________
```

---

### Delete a Task

- `delete {task number}` – Deletes the selected task from the list.

#### Example:
```
____________________________________________________________
You: delete 2
____________________________________________________________
Eddie:
Noted. I've removed this task:
  [D][ ] return book (by: Apr 1 2025, 6:00pm)
Now you have 2 tasks in the list.
____________________________________________________________
```

---

### Find Tasks

- `find {keyword}` – Search and display tasks that match the keyword.

#### Example:
```
____________________________________________________________
You: find book
____________________________________________________________
Eddie:
Here are the tasks matching your list:
1. [T][ ] read book
2. [D][ ] return book (by: Apr 1 2025, 6:00pm)
____________________________________________________________
```

---

### Storage

Eddie saves your task list in a text file at `data/tasks.txt`, and loads it every time you start the app.

---

## How to Use

1. Clone this repo and build the project in your IDE
2. Run `Eddie.java`.
3. Interact with Eddie through the terminal using the supported commands above.
4. Exit using the command `bye`.

---

