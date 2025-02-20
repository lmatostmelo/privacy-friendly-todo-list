/*
Privacy Friendly To-Do List
Copyright (C) 2024-2025  Christian Adams

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/
package org.secuso.privacyfriendlytodolist.model.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import org.secuso.privacyfriendlytodolist.model.database.entities.TodoListData

@Dao
interface TodoListDao {
    @Insert
    suspend fun insert(todoListData: TodoListData): Long

    @Update
    suspend fun update(todoListData: TodoListData): Int

    @Delete
    suspend fun delete(todoListData: TodoListData): Int

    @Query("DELETE FROM todoLists")
    suspend fun deleteAll(): Int

    @Query("SELECT COUNT(id) FROM todoLists")
    suspend fun getCount(): Int

    @Query("SELECT id FROM todoLists ORDER BY sortOrder ASC")
    suspend fun getAllIds(): Array<Int>

    @Query("SELECT id, name FROM todoLists ORDER BY sortOrder ASC")
    suspend fun getAllNames(): Array<IdNameTuple>

    @Query("SELECT * FROM todoLists ORDER BY sortOrder ASC")
    suspend fun getAll(): Array<TodoListData>

    @Query("SELECT * FROM todoLists WHERE id = :todoListId LIMIT 1")
    suspend fun getById(todoListId: Int): TodoListData?

    @Query("UPDATE todoLists SET sortOrder = :sortOrder WHERE id = :id")
    suspend fun updateSortOrder(id: Int, sortOrder: Int): Int

    @Query("UPDATE todoLists SET sortOrder = COALESCE((SELECT MAX(sortOrder) + 1 FROM todoLists), 0) WHERE id = :id")
    suspend fun updateSortOrderToLast(id: Int): Int
}