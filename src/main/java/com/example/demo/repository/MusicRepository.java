package com.example.demo.repository;

import com.example.demo.model.Album;
import com.example.demo.model.Artist;
import com.example.demo.model.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MusicRepository {
    @Autowired
    JdbcTemplate template;

    public List<Artist> fetchAllArtists() {
        String sql = "SELECT * FROM artist;";
        RowMapper<Artist> artistRowMapper = new BeanPropertyRowMapper<>(Artist.class);
        return template.query(sql, artistRowMapper);
    }

    public List<Album> fetchAlbumsByArtist(int artistId) {
        String sql = "SELECT * FROM album WHERE artist_id = " + artistId + ";";
        RowMapper<Album> albumRowMapper = new BeanPropertyRowMapper<>(Album.class);
        return template.query(sql, albumRowMapper);
    }

    public List<Song> fetchSongsByAlbumId(int albumId) {
        String sql = "SELECT * FROM song WHERE album_id = " + albumId + ";";
        RowMapper<Song> songRowMapper = new BeanPropertyRowMapper<>(Song.class);
        return template.query(sql, songRowMapper);
    }

    public void createArtist(Artist artist) {
        String sql = "INSERT INTO artist (artist_name) VALUES (?);";
        template.update(sql, artist.getArtistName());
    }

    public void addAlbum(Album album) {
        String sql = "INSERT INTO album (artist_id, album_name) VALUES (?, ?);";
        template.update(sql, album.getArtistId(), album.getAlbumName());
    }

    public void deleteArtist(int artistId) {
        deleteAlbum(artistId);
        String sql = "DELETE FROM artist WHERE artist_id = " + artistId + ";";
        template.execute(sql);
    }

    public void deleteAlbum(int artistId) {
        deleteSong(getAlbumIdByArtistId(artistId));
        String sql = "DELETE FROM album WHERE artist_id = " + artistId + ";";
        template.execute(sql);
    }

    public void deleteSong(int albumId) {
        String sql = "DELETE FROM song WHERE album_id = " + albumId + ";";
        template.execute(sql);
    }

    private int getAlbumIdByArtistId(int artistId) {
        String sql = "SELECT * FROM album WHERE artist_id = " + artistId + ";";
        RowMapper<Album> albumRowMapper = new BeanPropertyRowMapper<>(Album.class);
        List<Album> albumList = template.query(sql, albumRowMapper);
        return albumList.isEmpty() ? 0 : albumList.get(0).getAlbumId();
    }
}
