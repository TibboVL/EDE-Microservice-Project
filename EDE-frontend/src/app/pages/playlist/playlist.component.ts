import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Playlist } from 'src/app/models/playlist';
import { Observable, map } from 'rxjs';
import { PlaylistService } from 'src/services/playlist.service';
import { UserService } from 'src/services/user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { SongCardComponent } from '../../components/song-card/song-card.component';
import { SongService } from 'src/services/song.service';

@Component({
  selector: 'app-playlist',
  standalone: true,
  templateUrl: './playlist.component.html',
  styleUrls: ['./playlist.component.scss'],
  host: {
    class: 'w-full h-full',
  },
  imports: [CommonModule, FontAwesomeModule, SongCardComponent],
})
export class PlaylistComponent {
  playlist$: Observable<Playlist> = new Observable<Playlist>();
  private playlistId: string = '';

  constructor(
    private playlistService: PlaylistService,
    private songService: SongService,
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit() {
    this.route.paramMap.subscribe((params) => {
      const playlistId = params.get('id');
      this.playlistId = playlistId!;
      if (playlistId) {
        this.playlist$ = this.playlistService.getPlaylist(playlistId);
      }
    });
  }

  deletePlaylist() {
    this.playlistService.deletePlaylist(this.playlistId).subscribe((result) => {
      console.log(result);
      this.router.navigate(['home']);
      this.playlistService.playlistsModified.emit();
    });
  }

  songDurationToMinSec(duration: number): string {
    return this.songService.songDurationToMinSec(duration);
  }

  removeSongFromPlaylist(songId: string) {
    this.playlistService.removeSong(this.playlistId, songId).subscribe(
      (result) => {
        console.log(`succesfully removed ${songId}`);
        // Update the local playlists$ observable after successful removal
        // Update the local playlist$ observable after successful removal
        this.playlist$ = this.playlist$.pipe(
          map((playlist) => {
            // Update the songs array of the specific playlist
            playlist.songs = playlist.songs.filter(
              (song) => song.songId !== songId
            );
            return playlist;
          })
        );
      },
      (error) => {
        console.error('Error removing song:', error);
      }
    );
  }
}
