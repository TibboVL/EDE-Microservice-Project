export interface User {
  id: string;
  userId: string;
  firstname: string;
  lastname: string;
  username: string;
  email: string;
  dateOfBirth: Date;
  registrationDate: Date;

  // contains the ids of the liked song/playlist
  // better to just put these into a playlist?
  // likedSongs?: string[];
  myFavoritePlaylist: string;
  likedPlaylists: string[];
  // favoritePlaylists?: number[];
  // roleId?: number;
}

export interface UserFormModel {
  id?: string;
  userId: string;
  firstname: string;
  lastname: string;
  username: string;
  email: string;
  dateOfBirth: string;
  registrationDate?: string;
  // contains the ids of the liked song/playlist
  // better to just put these into a playlist?
  // likedSongs?: string[];
  myFavoritePlaylist?: string;
  likedPlaylists?: string[];
  // favoritePlaylists?: number[];
  // roleId?: number;
}
