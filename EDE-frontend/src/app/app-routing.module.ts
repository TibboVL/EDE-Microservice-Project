import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppComponent } from './app.component';
import { HomeComponent } from './pages/home/home.component';
import { PlaylistComponent } from './pages/playlist/playlist.component';

const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' }, // redirect root to home
  // { path: 'home', component: HomeComponent },
  { path: 'home', component: HomeComponent }, // Use the AuthGuard for home route
  { path: 'playlist/:id', component: PlaylistComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
