<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Task Management - Drag & Drop</title>
    <link rel="stylesheet" href="assets/css/drag.css"> <!-- Your custom CSS -->
    <script src="assets/js/drag.js" defer></script> <!-- Drag-and-drop logic -->
    <script src="assets/js/todo.js" defer></script> <!-- Add task logic -->
</head>
<body>
<div class="board">
    <form id="todo-form">
        <input type="text" placeholder="New Task..." id="todo-input" />
        <button type="submit">Add +</button>
    </form>

    <div class="lanes">
        <div class="swim-lane" id="todo-lane">
            <h3 class="heading">TODO</h3>

            <p class="task" draggable="true">Get groceries</p>
            <p class="task" draggable="true">Feed the dogs</p>
        </div>

        <div class="swim-lane">
            <h3 class="heading">Doing</h3>

            <p class="task" draggable="true">Work on Java project</p>
        </div>

        <div class="swim-lane">
            <h3 class="heading">Done</h3>

            <p class="task" draggable="true">Finish presentation</p>
        </div>
    </div>
</div>
</body>
</html>
