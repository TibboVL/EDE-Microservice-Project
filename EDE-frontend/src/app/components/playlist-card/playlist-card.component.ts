import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Playlist } from 'src/app/models/playlist';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-playlist-card',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './playlist-card.component.html',
  styleUrls: ['./playlist-card.component.scss'],
})
export class PlaylistCardComponent {
  @Input() playlist!: Playlist;
}
