package com.example.dailyplanner.data.network

class TaskNetworkMock: TaskApi {
    override suspend fun fetchAllTasks(): String {
        return """
            [
                {
                    "id": 0,
                    "date_start": 1733139498481,
                    "date_finish": 1733139517569,
                    "name": "Погладить собаку",
                    "description": "Описание моего дела 1",
                    "is_completed": false
                },
                {
                    "id": 1,
                    "date_start": 1733139498481,
                    "date_finish": 1733139517569,
                    "name": "Погладить кота",
                    "description": "Описание моего дела 1",
                    "is_completed": false
                },
                {
                    "id": 2,
                    "date_start": 1733139498481,
                    "date_finish": 1733139517569,
                    "name": "Помыть посуду",
                    "description": "Описание моего дела 2",
                    "is_completed": false
                },
                {
                    "id": 3,
                    "date_start": 1733086860000,
                    "date_finish": 1733089200000,
                    "name": "Погладить рубашку",
                    "description": "Описание моего дела 3",
                    "is_completed": false
                },
                {
                    "id": 4,
                    "date_start": 1733091000000,
                    "date_finish": 1733091000000,
                    "name": "Заправить авто",
                    "description": "Описание моего дела 4",
                    "is_completed": false
                },
                {
                    "id": 5,
                    "date_start": 1733094600000,
                    "date_finish": 1733094600000,
                    "name": "Посмотреть лекцию CS50",
                    "description": "Instructions Check the Material Symbols guide for advanced examples such as animations and font loading optimization. Variable icon font Add the variable font stylesheet request to your head tag and the current variable axes configuration to icons using CSS.",
                    "is_completed": false
                },
                {
                    "id": 6,
                    "date_start": 1733098200000,
                    "date_finish": 1733098200000,
                    "name": "Заняться каким-то делом",
                    "description": "Описание моего рандомного дела",
                    "is_completed": false
                },
                {
                    "id": 7,
                    "date_start": 1733101200000,
                    "date_finish": 17331012000000,
                    "name": "Task 7",
                    "description": "Описание моего дела 7",
                    "is_completed": false
                },
                {
                    "id": 8,
                    "date_start": 1733104800000,
                    "date_finish": 1733104800000,
                    "name": "Task 8",
                    "description": "Описание моего дела 8",
                    "is_completed": false
                },
                {
                    "id": 9,
                    "date_start": 1733108400000,
                    "date_finish": 1733108400000,
                    "name": "Task 9",
                    "description": "Описание моего дела 9",
                    "is_completed": false
                },
                {
                    "id": 10,
                    "date_start": 1733112000000,
                    "date_finish": 1733112000000,
                    "name": "Task 10",
                    "description": "Описание моего дела 10",
                    "is_completed": false
                },
                {
                    "id": 11,
                    "date_start": 1733101200000,
                    "date_finish": 1733101200000,
                    "name": "Task 11",
                    "description": "Описание моего дела 11",
                    "is_completed": false
                },
                {
                    "id": 12,
                    "date_start": 1733115600000,
                    "date_finish": 1733115600000,
                    "name": "Task 12",
                    "description": "Описание моего дела 12",
                    "is_completed": false
                },
                {
                    "id": 13,
                    "date_start": 1733119200000,
                    "date_finish": 1733119200000,
                    "name": "Task 13",
                    "description": "Описание моего дела 13",
                    "is_completed": false
                },
                {
                    "id": 14,
                    "date_start": 1733122800000,
                    "date_finish": 1733122800000,
                    "name": "Task 14",
                    "description": "Описание моего дела 14",
                    "is_completed": false
                },
                {
                    "id": 15,
                    "date_start": 1733126400000,
                    "date_finish": 1733126400000,
                    "name": "Task 15",
                    "description": "Описание моего дела 15",
                    "is_completed": false
                },
                {
                    "id": 16,
                    "date_start": 1733130000000,
                    "date_finish": 1733130000000,
                    "name": "Task 16",
                    "description": "Описание моего дела 16",
                    "is_completed": false
                },
                {
                    "id": 17,
                    "date_start": 1733133600000,
                    "date_finish": 1733133600000,
                    "name": "Task 17",
                    "description": "Описание моего дела 17",
                    "is_completed": false
                },
                {
                    "id": 18,
                    "date_start": 1733137200000,
                    "date_finish": 1733137200000,
                    "name": "Task 18",
                    "description": "Описание моего дела 18",
                    "is_completed": false
                },
                {
                    "id": 19,
                    "date_start": 1733140800000,
                    "date_finish": 1733140800000,
                    "name": "Task 19",
                    "description": "Описание моего дела 19",
                    "is_completed": false
                },
                {
                    "id": 20,
                    "date_start": 1733144400000,
                    "date_finish": 1733144400000,
                    "name": "Task 20",
                    "description": "Описание моего дела 20",
                    "is_completed": false
                },
                {
                    "id": 21,
                    "date_start": 1733148000000,
                    "date_finish": 1733148000000,
                    "name": "Task 21",
                    "description": "Описание моего дела 21",
                    "is_completed": false
                },
                {
                    "id": 22,
                    "date_start": 1733151600000,
                    "date_finish": 1733151600000,
                    "name": "Task 22",
                    "description": "Описание моего дела 22",
                    "is_completed": false
                },
                {
                    "id": 23,
                    "date_start": 1733155200000,
                    "date_finish": 1733155200000,
                    "name": "Task 23",
                    "description": "Описание моего дела 23",
                    "is_completed": false
                },
                {
                    "id": 24,
                    "date_start": 1733158800000,
                    "date_finish": 1733158800000,
                    "name": "Task 24",
                    "description": "Описание моего дела 24",
                    "is_completed": false
                },
                {
                    "id": 25,
                    "date_start": 1733162400000,
                    "date_finish": 1733162400000,
                    "name": "Task 25",
                    "description": "Описание моего дела 25",
                    "is_completed": false
                },
                {
                    "id": 26,
                    "date_start": 1733166000000,
                    "date_finish": 1733166000000,
                    "name": "Task 26",
                    "description": "Описание моего дела 26",
                    "is_completed": false
                },
                {
                    "id": 27,
                    "date_start": 1733169600000,
                    "date_finish": 1733169600000,
                    "name": "Task 27",
                    "description": "Описание моего дела 27",
                    "is_completed": false
                }
            ]
        """
    }
}