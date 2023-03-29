package com.example.demo.service;

import com.example.demo.model.Album;
import com.example.demo.model.Artist;
import com.example.demo.model.Song;
import com.example.demo.repository.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicService {
    @Autowired
    MusicRepository musicRepository;

    public List<Artist> fetchAllArtist() {
        return musicRepository.fetchAllArtists();
    }

    public List<Album> fetchAlbumsByArtist(int artistId) {
        return musicRepository.fetchAlbumsByArtist(artistId);
    }

    public List<Song> fetchSongsByAlbumId(int albumId) {
        return musicRepository.fetchSongsByAlbumId(albumId);
    }

    public void deleteArtist(int artistId) {
        musicRepository.deleteArtist(artistId);
    }

    public void createArtist(Artist artist) {
        musicRepository.createArtist(artist);
    }

    public void addAlbum(Album album) {
        musicRepository.addAlbum(album);
    }
}
