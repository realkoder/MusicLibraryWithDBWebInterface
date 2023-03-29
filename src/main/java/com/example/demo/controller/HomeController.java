package com.example.demo.controller;

import com.example.demo.model.Album;
import com.example.demo.model.Artist;
import com.example.demo.model.Song;
import com.example.demo.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Controller
public class HomeController {
    @Autowired
    MusicService musicService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("showDialog", false);
        List<Artist> artistList = musicService.fetchAllArtist();
        model.addAttribute("artists", artistList);
        return "home/index";
    }

    @PostMapping("/viewAlbum")
    public String viewAlbum(Model model, @RequestParam("artistId") Integer artistId) {
        List<Album> albumList = musicService.fetchAlbumsByArtist(artistId);
        model.addAttribute("albums", albumList);
        model.addAttribute("artistId", artistId);
        System.out.println(artistId);
        return "home/album";
    }

    @PostMapping("/viewSong")
    public String viewSong(Model model, @RequestParam("albumId") int albumId) {
        List<Song> songList = musicService.fetchSongsByAlbumId(albumId);
        model.addAttribute("songs", songList);
        return "home/songs";
    }

    @PostMapping("/deleteArtist")
    public String deleteArtist(Model model, @RequestParam("artistId") int artistId) {
        model.addAttribute("showDialog", true);
        model.addAttribute("completed", true);
        musicService.deleteArtist(artistId);
        List<Artist> artistList = musicService.fetchAllArtist();
        model.addAttribute("artists", artistList);
        return "home/index";
    }

    @GetMapping("setupCreateArtist")
    public String setupCreateArtist() {
        return "home/createArtist";
    }

    @PostMapping("/createNewArtist")
    public String create(Model model, @ModelAttribute Artist artist) {
        model.addAttribute("showDialog", true);
        if (artist.getArtistName().length() > 0 && artist.getArtistName().length() < 60) {
            musicService.createArtist(artist);
            model.addAttribute("completed", true);
        } else {
            model.addAttribute("completed", false);
        }
        List<Artist> artistList = musicService.fetchAllArtist();
        model.addAttribute("artists", artistList);
        return "home/index";
    }

    @PostMapping("setupAddAlbum")
    public String setupCreateArtist(@RequestParam("artistId") Integer artistId, Model model) {
        model.addAttribute("finalArtistId", artistId);
        return "home/addAlbum";
    }

    @PostMapping("/addNewAlbum")
    public String addNewAlbum(@ModelAttribute Album album, Model model) {
        if (album.getAlbumName().length() > 0 && album.getAlbumName().length() < 60) {
            musicService.addAlbum(album);
            model.addAttribute("completed", true);
        } else {
            model.addAttribute("completed", false);
        }
        List<Artist> artistList = musicService.fetchAllArtist();
        model.addAttribute("artists", artistList);
        model.addAttribute("showDialog", true);
        return "home/index";
    }
}
