package com.rejowan.videoapp.data.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.rejowan.videoapp.model.VideoModel

class SQLiteDBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "video_database.db"
        private const val DATABASE_VERSION = 1

        // Table Name
        private const val TABLE_NAME = "videos"

        // Column Names
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_THUMBNAIL_URL = "thumbnail_url"
        private const val COLUMN_DURATION = "duration"
        private const val COLUMN_UPLOAD_TIME = "upload_time"
        private const val COLUMN_VIEWS = "views"
        private const val COLUMN_AUTHOR = "author"
        private const val COLUMN_VIDEO_URL = "video_url"
        private const val COLUMN_DESCRIPTION = "description"
        private const val COLUMN_SUBSCRIBER = "subscriber"
        private const val COLUMN_IS_LIVE = "is_live"

        private const val CREATE_TABLE_QUERY =
            "CREATE TABLE IF NOT EXISTS $TABLE_NAME ($COLUMN_ID TEXT,$COLUMN_TITLE TEXT,$COLUMN_THUMBNAIL_URL TEXT,$COLUMN_DURATION TEXT,$COLUMN_UPLOAD_TIME TEXT,$COLUMN_VIEWS TEXT,$COLUMN_AUTHOR TEXT,$COLUMN_VIDEO_URL TEXT,$COLUMN_DESCRIPTION TEXT,$COLUMN_SUBSCRIBER TEXT,$COLUMN_IS_LIVE INTEGER)"

        private const val DROP_TABLE_QUERY = "DROP TABLE IF EXISTS $TABLE_NAME"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DROP_TABLE_QUERY)
        onCreate(db)
    }

    // ------------------------ CREATE ------------------------

    fun insertVideos(videos: List<VideoModel>) {
        val db = writableDatabase
        db.beginTransaction()
        try {
            videos.forEach { video ->
                val query =
                    "INSERT INTO $TABLE_NAME ($COLUMN_ID, $COLUMN_TITLE, $COLUMN_THUMBNAIL_URL, $COLUMN_DURATION, $COLUMN_UPLOAD_TIME, $COLUMN_VIEWS, $COLUMN_AUTHOR, $COLUMN_VIDEO_URL, $COLUMN_DESCRIPTION, $COLUMN_SUBSCRIBER, $COLUMN_IS_LIVE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
                val statement = db.compileStatement(query)
                statement.bindString(1, video.id)
                statement.bindString(2, video.title)
                statement.bindString(3, video.thumbnailUrl)
                statement.bindString(4, video.duration)
                statement.bindString(5, video.uploadTime)
                statement.bindString(6, video.views)
                statement.bindString(7, video.author)
                statement.bindString(8, video.videoUrl)
                statement.bindString(9, video.description)
                statement.bindString(10, video.subscriber)
                statement.bindLong(11, if (video.isLive) 1 else 0)
                statement.executeInsert()
            }
            db.setTransactionSuccessful()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.endTransaction()
        }
    }

    // ------------------------ READ ------------------------

    fun getVideos(): List<VideoModel> {
        val videos = mutableListOf<VideoModel>()
        val db = readableDatabase
        val cursor = db.query(TABLE_NAME, null, null, null, null, null, null)
        cursor.use {
            while (it.moveToNext()) {
                val video = VideoModel(
                    it.getString(it.getColumnIndexOrThrow(COLUMN_ID)),
                    it.getString(it.getColumnIndexOrThrow(COLUMN_TITLE)),
                    it.getString(it.getColumnIndexOrThrow(COLUMN_THUMBNAIL_URL)),
                    it.getString(it.getColumnIndexOrThrow(COLUMN_DURATION)),
                    it.getString(it.getColumnIndexOrThrow(COLUMN_UPLOAD_TIME)),
                    it.getString(it.getColumnIndexOrThrow(COLUMN_VIEWS)),
                    it.getString(it.getColumnIndexOrThrow(COLUMN_AUTHOR)),
                    it.getString(it.getColumnIndexOrThrow(COLUMN_VIDEO_URL)),
                    it.getString(it.getColumnIndexOrThrow(COLUMN_DESCRIPTION)),
                    it.getString(it.getColumnIndexOrThrow(COLUMN_SUBSCRIBER)),
                    it.getInt(it.getColumnIndexOrThrow(COLUMN_IS_LIVE)) == 1
                )
                videos.add(video)
            }
        }
        return videos
    }

    // ------------------------ UPDATE ------------------------

    fun updateVideos(videos: List<VideoModel>) {
        val db = writableDatabase
        db.beginTransaction()
        try {
            videos.forEach { video ->
                val query =
                    "UPDATE $TABLE_NAME SET $COLUMN_TITLE = ?, $COLUMN_THUMBNAIL_URL = ?, $COLUMN_DURATION = ?, $COLUMN_UPLOAD_TIME = ?, $COLUMN_VIEWS = ?, $COLUMN_AUTHOR = ?, $COLUMN_VIDEO_URL = ?, $COLUMN_DESCRIPTION = ?, $COLUMN_SUBSCRIBER = ?, $COLUMN_IS_LIVE = ? WHERE $COLUMN_ID = ?"
                val statement = db.compileStatement(query)
                statement.bindString(1, video.title)
                statement.bindString(2, video.thumbnailUrl)
                statement.bindString(3, video.duration)
                statement.bindString(4, video.uploadTime)
                statement.bindString(5, video.views)
                statement.bindString(6, video.author)
                statement.bindString(7, video.videoUrl)
                statement.bindString(8, video.description)
                statement.bindString(9, video.subscriber)
                statement.bindLong(10, if (video.isLive) 1 else 0)
                statement.bindString(11, video.id)
                statement.executeUpdateDelete()
            }
            db.setTransactionSuccessful()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.endTransaction()
        }
    }

    // ------------------------ DELETE ------------------------

    fun deleteVideos(videos: List<VideoModel>) {
        val db = writableDatabase
        db.beginTransaction()
        try {
            videos.forEach { video ->
                val query = "DELETE FROM $TABLE_NAME WHERE $COLUMN_ID = ?"
                val statement = db.compileStatement(query)
                statement.bindString(1, video.id)
                statement.executeUpdateDelete()
            }
            db.setTransactionSuccessful()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.endTransaction()
        }
    }


}