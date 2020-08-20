package code.name.monkey.retromusic.db

import androidx.room.*

@Dao
interface PlaylistDao {

    @Query("SELECT * FROM PlaylistEntity WHERE playlist_name = :name")
    suspend fun checkPlaylistExists(name: String): List<PlaylistEntity>

    @Insert
    suspend fun createPlaylist(playlistEntity: PlaylistEntity): Long

    @Query("SELECT * FROM PlaylistEntity")
    suspend fun playlists(): List<PlaylistEntity>

    @Transaction
    @Query("SELECT * FROM PlaylistEntity")
    suspend fun playlistsWithSong(): List<PlaylistWithSongs>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSongs(songEntities: List<SongEntity>)

    @Query("SELECT * FROM SongEntity WHERE playlist_creator_id = :playlistName AND id = :songId")
    suspend fun checkSongExistsWithPlaylistName(playlistName: String, songId: Int): List<SongEntity>

    @Query("SELECT * FROM SongEntity WHERE playlist_creator_id = :playlistId ORDER BY title")
    suspend fun getSongs(playlistId: Int): List<SongEntity>

    @Delete
    suspend fun deletePlaylistEntity(playlistWithSongs: PlaylistWithSongs)
}