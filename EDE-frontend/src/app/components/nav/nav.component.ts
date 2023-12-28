import {
  Component,
  ElementRef,
  HostListener,
  Inject,
  ViewChild,
  isDevMode,
} from '@angular/core';
import { CommonModule, DOCUMENT } from '@angular/common';
import { OAuthService } from 'angular-oauth2-oidc';
import { UserService } from 'src/services/user.service';

@Component({
  selector: 'app-nav',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.scss'],
})
export class NavComponent {
  public user: any;
  devMode: boolean = isDevMode();
  dropdownExpanded = false;
  @ViewChild('profile') profile!: ElementRef;

  // close popup if clicked outside of it
  @HostListener('window:click', ['$event'])
  closePopup(event: Event) {
    this.dropdownExpanded = false;
  }

  // prevent closing of popup if clicked within it
  ngAfterViewInit() {
    this.profile.nativeElement.addEventListener('click', (event: Event) => {
      event.stopPropagation();
    });
  }

  constructor(
    @Inject(DOCUMENT) public document: Document,

    private oauthService: OAuthService,
    public userService: UserService
  ) {}

  ngOnInit() {
    this.userService.user$.subscribe((user) => {
      // console.log(user);
      this.user = user;
    });
  }

  get isAuthenticated(): boolean {
    return this.oauthService.hasValidAccessToken();
  }

  login() {
    this.oauthService.initCodeFlow();
  }

  logout() {
    this.oauthService.logOut();
  }
}
