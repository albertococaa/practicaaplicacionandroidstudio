package com.example.myapplication.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.RoomDatabase.Callback
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [UserEntity::class, CartItemEntity::class],
    version = 2,               // <-- versión incrementada
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun cartDao(): CartDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(ctx: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(ctx).also { INSTANCE = it }
            }

        private fun buildDatabase(ctx: Context): AppDatabase =
            Room.databaseBuilder(
                ctx.applicationContext,
                AppDatabase::class.java,
                "app_db"
            )
                .fallbackToDestructiveMigration() // <-- borrará / recreará la BBDD al cambiar versión
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // Pre‐poblar la tabla de usuarios
                        CoroutineScope(Dispatchers.IO).launch {
                            getInstance(ctx).userDao().insert(
                                UserEntity(
                                    username = "admin",
                                    password = "1234"
                                )
                            )
                        }
                    }
                })
                .build()
    }
}
