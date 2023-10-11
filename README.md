#EDE Project Documentation

Welcome to the documentation for the EDE (Your Project Name) repository. This document provides an overview of the project's services, endpoints, and their connections.

### Services structure

![[projectDiagram.png]]
### User service

This service manages user-related operations, such as user registration, updating user information, and account deletion.

| Call Type | Endpoints                                   | Comments                                            |
|-----------|--------------------------------------------|----------------------------------------------------|
| POST      | - /user                                  | Create a new user.                                  |
| GET       | - /user/{userId}                         | Retrieve user details by ID.                        |
| PUT       | - /user/{userId}                         | Update user information.                            |
| DELETE    | - /user/{userId}                         | Delete a user account.                              |
### Library service

The Library Service is responsible for managing music and podcast content, including adding, updating, and deleting songs and podcast episodes.

| Call Type | Endpoints                                   | Comments                                            |
|-----------|--------------------------------------------|----------------------------------------------------|
| POST      | - /library/song                           | Add a new song to the library.                     |
| GET       | - /library/song/{songId}                  | Retrieve song details by ID.                       |
| PUT       | - /library/song/{songId}                  | Update song information.                           |
| DELETE    | - /library/song/{songId}                  | Delete a song from the library.                   |
| POST      | - /library/podcasts                        | Add a new podcast episode to the library.         |
| GET       | - /library/podcasts/{podcastId}             | Retrieve podcast episode details by ID.           |
| PUT       | - /library/podcasts/{podcastId}             | Update podcast episode information.               |
| DELETE    | - /library/podcasts/{podcastId}             | Delete a podcast episode from the library.        |
### Playlist service

This service manages user playlists, allowing users to create, update, and delete playlists, and add or remove songs from playlists.

| Call Type | Endpoints                                   | Comments                                            |
|-----------|--------------------------------------------|----------------------------------------------------|
| POST      | - /playlist                                | Create a new playlist.                             |
| GET       | - /playlist/{playlistId}                   | Get a playlist.                                    |
| GET       | - /playlist/user/{userId}                  | Get a user's playlists.                            |
| PUT       | - /playlist/{playlistId}                   | Update playlist info.                              |
| PUT       | - /playlist/{playlistId}/{songId}          | Add a song to playlist.                            |
| DELETE    | - /playlist/{playlistId}/{songId}           | Remove song from playlist.                         |
| DELETE    | - /playlist/{playlistId}                   | Delete playlist.                                   |
### Rating service

This optional service manages user ratings for songs and podcast episodes.

| Call Type | Endpoints                                   | Comments                                            |
|-----------|--------------------------------------------|----------------------------------------------------|
| POST      | - /rating                                 | Rate a song or podcast episode.                   |
| GET       | - /rating/{itemId}                        | Retrieve ratings for a specific item.              |
| PUT       | - /rating/{itemId}                        | Update a rating for an item.                      |
| DELETE    | - /rating/{itemId}                        | Delete a rating for an item.                      |

