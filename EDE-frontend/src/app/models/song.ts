// properties zijn HOOFDLETTERGEVOELIG!!!!!!
export interface Song {
  id: string;
  title: string;
  artistId: string;
  album: string;
  genre: string;
  duration: number;
  releaseDate: Date;
  path: string;
  coverArt: string;
  plays: number;
  likes: number;
}
