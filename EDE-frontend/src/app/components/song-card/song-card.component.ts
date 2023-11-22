import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Song } from 'src/app/models/song';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { stringify } from 'postcss';
import { SongService } from 'src/services/song.service';

@Component({
  selector: 'app-song-card',
  standalone: true,
  imports: [CommonModule, FontAwesomeModule],
  templateUrl: './song-card.component.html',
  styleUrls: ['./song-card.component.scss'],
})
export class SongCardComponent {
  constructor(private songService: SongService) {}

  @Input() song!: Song;

  getRandomNumber() {
    return Math.floor(Math.random() * 100);
  }

  delete(songId: string) {
    this.songService.deleteSong(songId).subscribe((Response) => {
      console.log(Response);
    });
  }
  songDurationToMinSec(duration: number): string {
    return (
      Math.floor(duration / 60) +
      ':' +
      ('00' + (duration % 60).toString()).slice(-2)
    );
  }
}
