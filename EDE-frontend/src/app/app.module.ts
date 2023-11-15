import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { SongCardComponent } from './components/song-card/song-card.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NavComponent } from './nav/nav.component';
import { AppService } from './app.service';

@NgModule({
  declarations: [AppComponent],
  providers: [AppService],
  bootstrap: [AppComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    SongCardComponent,
    NavComponent,
    FontAwesomeModule,
    /*     OAuthModule.forRoot({
      resourceServer: {
        allowedUrls: ['api-tibbovl.cloud.okteto.net'],
        sendAccessToken: true,
      },
    }), */
    NavComponent,
  ],
})
export class AppModule {}
