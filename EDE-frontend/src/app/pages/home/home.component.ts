import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Observable, of } from 'rxjs';
import { Song } from '../../models/song';
import { SongService } from '../../../services/song.service';
import { SongCardComponent } from '../../components/song-card/song-card.component';
import { UserService } from 'src/services/user.service';
import { Playlist } from 'src/app/models/playlist';
import { PlaylistService } from 'src/services/playlist.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, SongCardComponent],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  host: {
    class: 'w-full',
  },
})
export class HomeComponent {
  popularSongs$: Observable<Song[]> = new Observable<Song[]>();
  playlists$: Observable<Playlist[]> = new Observable<Playlist[]>();
  favoritesPlaylist$: Observable<Playlist> = new Observable<Playlist>();

  constructor(
    private songService: SongService,
    private playlistService: PlaylistService,
    public userService: UserService
  ) {}

  ngOnInit() {
    this.popularSongs$ = this.songService.getTopSongs(5);

    this.userService.user$.subscribe((user) => {
      if (user) {
        this.playlists$ = this.playlistService.getPlaylistFromUser(user.sub);

        this.favoritesPlaylist$ = this.playlistService.getMyFavoritesPlaylist(
          user.id
        );

        this.playlistService.playlistsModified.subscribe(() => {
          this.playlists$ = this.playlistService.getPlaylistFromUser(user.sub);
        });

        this.playlists$.subscribe((result) => {
          console.log(result);
        });
      }
    });
  }
}
