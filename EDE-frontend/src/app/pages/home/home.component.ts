import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Observable } from 'rxjs';
import { Song } from '../../models/song';
import { SongService } from '../../../services/song.service';
import { SongCardComponent } from '../../components/song-card/song-card.component';
import { UserService } from 'src/services/user.service';

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
  songs$: Observable<Song[]> = new Observable<Song[]>();

  constructor(
    private songService: SongService,
    public userService: UserService
  ) {}

  ngOnInit() {
    this.songs$ = this.songService.getAllSongs();
  }
}
