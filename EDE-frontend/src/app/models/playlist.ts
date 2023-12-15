import { PartialSong } from './partialSong';
import { Song } from './song';

export interface Playlist {
  id: string;
  userId: string;
  name: string;
  description: string;
  isPublic: boolean;
  isFavorite: boolean;
  listType: ListType;
  songs: PartialSong[];
}

export enum ListType {
  playlist,
  album,
}

export interface PlaylistFormModel {
  userId: string;
  name: string;
  description: string;
  isPublic: boolean;
  //   isFavorite: boolean;
  listType: ListType;
}
