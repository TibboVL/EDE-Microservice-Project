import { Component, ElementRef, HostListener, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PlaylistCardComponent } from '../playlist-card/playlist-card.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { PlaylistService } from 'src/services/playlist.service';
import { UserService } from 'src/services/user.service';
import { Observable } from 'rxjs';
import { ListType, Playlist, PlaylistFormModel } from 'src/app/models/playlist';
import { ButtonComponent } from '../button/button.component';
import { ModalComponent } from '../modal/modal.component';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss'],
  imports: [
    CommonModule,
    PlaylistCardComponent,
    FontAwesomeModule,
    ButtonComponent,
    ModalComponent,
    FormsModule,
  ],
})
export class SidebarComponent {
  playlists$: Observable<Playlist[]> = new Observable<Playlist[]>();
  public isModalVisible: boolean = false;
  public model!: PlaylistFormModel;

  constructor(
    private playlistService: PlaylistService,
    private userService: UserService
  ) {}

  ngOnInit() {
    this.userService.user$.subscribe((user) => {
      if (user) {
        const userId = user.sub;
        this.playlists$ = this.playlistService.getPlaylistFromUser(userId);

        this.model = {
          name: '',
          userId: user.sub,
          description: '',
          isPublic: false,
          listType: ListType.playlist,
        };
      }
    });
  }

  openModal() {
    this.isModalVisible = true;
  }

  closeModal() {
    console.log('Modal closed!');
    this.isModalVisible = false;
  }

  createPlaylist() {
    // extra validation
    // api call to save
    console.warn({
      ...this.model,
    });

    this.playlistService.createPlaylist(this.model).subscribe((result) => {
      console.log(`create playlist ${this.model.name}`);
      this.closeModal();
    });
  }
}
