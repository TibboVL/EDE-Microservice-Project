import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Playlist } from 'src/app/models/playlist';
import { Observable } from 'rxjs';
import { PlaylistService } from 'src/services/playlist.service';
import { UserService } from 'src/services/user.service';
import { ActivatedRoute } from '@angular/router';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

@Component({
  selector: 'app-playlist',
  standalone: true,
  imports: [CommonModule, FontAwesomeModule],
  templateUrl: './playlist.component.html',
  styleUrls: ['./playlist.component.scss'],
  host: {
    class: 'w-full h-full',
  },
})
export class PlaylistComponent {
  playlist$: Observable<Playlist> = new Observable<Playlist>();
  private playlistId: string = '';

  constructor(
    private playlistService: PlaylistService,
    private userService: UserService,
    private route: ActivatedRoute
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
      this.playlistService.playlistsModified.emit();
    });
  }
}
