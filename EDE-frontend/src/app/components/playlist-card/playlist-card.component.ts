import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Playlist } from 'src/app/models/playlist';
import { RouterLink, RouterModule } from '@angular/router';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

@Component({
  selector: 'app-playlist-card',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterModule, FontAwesomeModule],
  templateUrl: './playlist-card.component.html',
  styleUrls: ['./playlist-card.component.scss'],
})
export class PlaylistCardComponent {
  @Input() playlist!: Playlist;
}
