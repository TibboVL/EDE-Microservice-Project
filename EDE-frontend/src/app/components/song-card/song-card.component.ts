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
  @Input() song!: Song;
  private randomNumber!: number;

  constructor(private songService: SongService) {}

  ngOnInit() {
    this.randomNumber = this.getRandomNumber();
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
    return (
      Math.floor(duration / 60) +
      ':' +
      ('00' + (duration % 60).toString()).slice(-2)
    );
  }
}
