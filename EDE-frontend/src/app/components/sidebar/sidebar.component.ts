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
import { RouterLink, RouterModule } from '@angular/router';
import { User } from 'src/app/models/user';
import { SidebarService } from 'src/services/sidebar.service';

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
    RouterLink,
    RouterModule,
  ],
})
export class SidebarComponent {
  playlists$: Observable<Playlist[]> = new Observable<Playlist[]>();
  favoritesPlaylis$: Observable<Playlist> = new Observable<Playlist>();
  public isModalVisible: boolean = false;
  public model!: PlaylistFormModel;
  private user!: any;

  constructor(
    private playlistService: PlaylistService,
    private userService: UserService,
    public sidebarService: SidebarService
  ) {}

  ngOnInit() {
    this.userService.user$.subscribe((user) => {
      if (user) {
        // const userId = user.sub;
        this.playlists$ = this.playlistService.getPlaylistFromUser(user.sub);

        this.model = {
          name: '',
          userId: user.sub,
          description: '',
          isPublic: false,
          listType: ListType.playlist,
        };
        this.user = this.model;

        //console.log(user);
        this.favoritesPlaylis$ = this.playlistService.getMyFavoritesPlaylist(
          user.id
        );

        this.playlistService.playlistsModified.subscribe(() => {
          this.playlists$ = this.playlistService.getPlaylistFromUser(user.sub);
        });
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
      this.playlistService.playlistsModified.emit();
      this.closeModal();

      this.model.name = '';
      this.model.description = '';
      this.model.isPublic = false;
      this.user = this.model;
    });
  }
}
