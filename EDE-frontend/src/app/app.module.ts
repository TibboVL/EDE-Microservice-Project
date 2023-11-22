import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { SongCardComponent } from './components/song-card/song-card.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NavComponent } from './components/nav/nav.component';
import { OAuthModule } from 'angular-oauth2-oidc';
import { environment } from 'src/environments/environment.development';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { HomeComponent } from './pages/home/home.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
  declarations: [AppComponent],
  providers: [],
  bootstrap: [AppComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    SongCardComponent,
    SidebarComponent,
    NavComponent,
    FontAwesomeModule,
    HomeComponent,
    BrowserAnimationsModule,
    OAuthModule.forRoot({
      resourceServer: {
        allowedUrls: [
          'https://accounts.google.com',
          environment.apiBaseURL + 'oauth',
        ],
        sendAccessToken: true,
      },
    }),
    NavComponent,
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class AppModule {}
