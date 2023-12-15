import {
  ChangeDetectorRef,
  Component,
  Input,
  SimpleChanges,
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { Song } from 'src/app/models/song';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { stringify } from 'postcss';
import { SongService } from 'src/services/song.service';
import { PlaylistService } from 'src/services/playlist.service';
import { Playlist } from 'src/app/models/playlist';
import { Observable, of } from 'rxjs';
import { ModalComponent } from '../modal/modal.component';
import { FormsModule } from '@angular/forms';
import { ButtonComponent } from '../button/button.component';

@Component({
  selector: 'app-song-card',
  standalone: true,
  templateUrl: './song-card.component.html',
  styleUrls: ['./song-card.component.scss'],
  imports: [
    CommonModule,
    FontAwesomeModule,
    ModalComponent,
    FormsModule,
    ButtonComponent,
  ],
})
export class SongCardComponent {
  public isModalVisible: boolean = false;

  @Input() song!: Song;
  @Input() playlists!: Playlist[] | null;
  @Input() favoritesPlaylist!: Playlist | null;
  private randomNumber!: number;
  private songBeingAdded: string | undefined = undefined;
  public inFavorites: Observable<boolean> = of(false);

  constructor(
    private songService: SongService,
    private playlistService: PlaylistService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.randomNumber = this.getRandomNumber();
  }

  // set the favorite button once the favoriteplaylist is loaded
  ngOnChanges(changes: SimpleChanges) {
    if (this.favoritesPlaylist) {
      this.isInFavorites(this.song.id);
    }
  }

  getRandomNumber() {
    return Math.floor(Math.random() * 100);
  }

  getCoverArtUrl(): string {
    return this.song.coverArt == null
      ? 'https://source.unsplash.com/random?sig=' + this.randomNumber
      : this.song.coverArt;
  }

  delete(songId: string) {
    this.songService.deleteSong(songId).subscribe((Response) => {
      console.log(Response);
    });
  }
  songDurationToMinSec(duration: number): string {
    return this.songService.songDurationToMinSec(duration);
  }

  addToPlaylist(playlistId: string) {
    this.playlistService.addSong(playlistId, this.songBeingAdded!).subscribe(
      (response) => {
        console.log(response);
        this.closeModal();
      },
      (error) => {
        if (error.status == 403) {
          console.log(`could not add ${this.songBeingAdded} to ${playlistId}`);
          alert('This song is already in the playlist');
        } else {
          console.log(`unknown error when trying to add song to playlist`);
          alert('unknown error when trying to add song to playlist');
        }
      }
    );
  }

  addToFavorites(songId: string) {
    this.playlistService.addSong(this.favoritesPlaylist!.id, songId).subscribe(
      (response) => {
        console.log(response);
        this.inFavorites = of(true);
      },
      (error) => {
        if (error.status == 403) {
          console.log(`could not add ${songId} to favorites`);
          alert('This song is already in the playlist');
        } else {
          console.log(`unknown error when trying to add song to playlist`);
          alert('unknown error when trying to add song to playlist');
        }
      }
    );
  }

  isInFavorites(songId: string) {
    if (this.favoritesPlaylist!.songs.some((song) => song.songId === songId)) {
      this.inFavorites = of(true);
      // console.log('true');
    } else {
      this.inFavorites = of(false);
      // console.log('false');
    }
  }

  /* 
    Modal
  */
  openModal(songId: string) {
    this.songBeingAdded = songId;
    this.isModalVisible = true;
  }

  closeModal() {
    console.log('Modal closed!');
    this.isModalVisible = false;
  }
}
