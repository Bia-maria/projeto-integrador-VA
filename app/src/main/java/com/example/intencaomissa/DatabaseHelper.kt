package com.example.intencaomissa

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        private const val DB_NAME = "intencoes.db"
        private const val DB_VERSION = 1

        const val TABLE_NAME = "intencoes"
        const val COL_ID = "id"
        const val COL_NOME = "nome"
        const val COL_INTENCAO = "intencao"
        const val COL_TIPO = "tipo"
        const val COL_DATA = "data"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_NOME TEXT NOT NULL,
                $COL_INTENCAO TEXT NOT NULL,
                $COL_TIPO TEXT NOT NULL,
                $COL_DATA TEXT NOT NULL
            );
        """.trimIndent()

        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun inserirIntencao(nome: String, intencao: String, tipo: String, data: String): Boolean {
        val db = writableDatabase
        val cv = ContentValues()

        cv.put(COL_NOME, nome)
        cv.put(COL_INTENCAO, intencao)
        cv.put(COL_TIPO, tipo)
        cv.put(COL_DATA, data)

        val result = db.insert(TABLE_NAME, null, cv)
        db.close()
        return result != -1L
    }

    fun listarIntencoes(): List<Intencao> {
        val lista = mutableListOf<Intencao>()
        val db = readableDatabase
        val cursor: Cursor = db.query(
            TABLE_NAME,
            arrayOf(COL_ID, COL_NOME, COL_INTENCAO, COL_TIPO, COL_DATA),
            null, null, null, null,
            "$COL_DATA ASC" // ordena por data (ajuste se quiser)
        )

        cursor.use {
            if (it.moveToFirst()) {
                do {
                    val id = it.getInt(it.getColumnIndexOrThrow(COL_ID))
                    val nome = it.getString(it.getColumnIndexOrThrow(COL_NOME))
                    val intencao = it.getString(it.getColumnIndexOrThrow(COL_INTENCAO))
                    val tipo = it.getString(it.getColumnIndexOrThrow(COL_TIPO))
                    val data = it.getString(it.getColumnIndexOrThrow(COL_DATA))

                    lista.add(Intencao(id, nome, intencao, tipo, data))
                } while (it.moveToNext())
            }
        }

        db.close()
        return lista
    }

    fun getIntencaoById(id: Int): Intencao? {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            arrayOf(COL_ID, COL_NOME, COL_INTENCAO, COL_TIPO, COL_DATA),
            "$COL_ID = ?",
            arrayOf(id.toString()),
            null, null, null
        )

        cursor.use {
            if (it.moveToFirst()) {
                val nome = it.getString(it.getColumnIndexOrThrow(COL_NOME))
                val intencao = it.getString(it.getColumnIndexOrThrow(COL_INTENCAO))
                val tipo = it.getString(it.getColumnIndexOrThrow(COL_TIPO))
                val data = it.getString(it.getColumnIndexOrThrow(COL_DATA))
                db.close()
                return Intencao(id, nome, intencao, tipo, data)
            }
        }

        db.close()
        return null
    }

    fun atualizarIntencao(id: Int, nome: String, intencao: String, tipo: String, data: String): Boolean {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(COL_NOME, nome)
        cv.put(COL_INTENCAO, intencao)
        cv.put(COL_TIPO, tipo)
        cv.put(COL_DATA, data)

        val rows = db.update(TABLE_NAME, cv, "$COL_ID = ?", arrayOf(id.toString()))
        db.close()
        return rows > 0
    }

    fun excluirIntencao(id: Int): Boolean {
        val db = writableDatabase
        val rows = db.delete(TABLE_NAME, "$COL_ID = ?", arrayOf(id.toString()))
        db.close()
        return rows > 0
    }
}
