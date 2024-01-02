# EDE Project Documentation

Welcome to the documentation for my Enterprise Development Experience repository. This document provides an overview of the project's services, endpoints, and their connections.

## Project theme

My EDE project centers around a music service, featuring functionalities such as playlist management, song library maintenance, user registration, and ratings for songs. The project's documentation illustrates the structure of the services, including their endpoints and operations.

## Services structure

![Project diagram](projectDiagram.png)

### Gateway

Serving as a link between various services, the gateway streamlines the user experience by converting specific service URLs to a unified format, reducing the complexity associated with managing multiple ports. It also plays a crucial role in enforcing a robust authentication system, ensuring secure access to the services, with the exception of specific exempted endpoints that are publicly accessible. 
URL conversions:

| Original URL                                    | Converted URL                              |
| ---------------------------------------------- | ---------------------------------------- |
| `http://localhost:8081/api/user/{id}`           | `http://localhost:8080/user/{id}`         |
| `http://localhost:8082/api/library/song/{songId}` | `http://localhost:8080/song/{songId}`     |
| `http://localhost:8083/api/playlist/{playlistId}` | `http://localhost:8080/playlist/{playlistId}` |
| `http://localhost:8084/api/rating/{itemId}`     | `http://localhost:8080/rating/{itemId}`   |


This centralized approach not only simplifies user interaction but also ensures that data security is maintained, guaranteeing that only authorized users can access the services.

### User service

This service manages user-related operations, such as user registration, updating user information, and account deletion.

| Mapping | Endpoints        | Comments                     | Requires Auth |
| ------- | ---------------- | ---------------------------- | ------------- |
| POST    | `/user`          | Create a new user.           | Yes            |
| GET     | `/user/{userId}` | Retrieve user details by ID. | Yes            |
| PUT     | `/user/{userId}` | Update user information.     | Yes            |
| DELETE  | `/user/{userId}` | Delete a user account.       | Yes            |
| GET  | `/user/all` | Get all users (debugging).       | Yes            |
| GET  | `/user/checkExistence/{id}` | Check if user exists in db (frontend)  | Yes            |
| GET  | `/user/dummyData` | Add some dummy data (debugging).       | Yes            |

### Library service

The Library Service is responsible for managing music content, including adding, updating, and deleting songs.

| Mapping | Endpoints                       | Comments                                   | Requires Auth |
| --------- | ------------------------------- | ------------------------------------------ | ------------------- |
| POST      | `/song`                 | Add a new song to the library.             | Yes |
| GET       | `/song/{songId}`        | Retrieve song details by ID.               | No |
| PUT       | `/song/{songId}`        | Update song information.                   | Yes |
| DELETE    | `/song/{songId}`        | Delete a song from the library.            | Yes |
| GET  | `/song/all` | Get all songs (debugging).       | Yes            |
| GET  | `/song/top/{count}` | Get a couple random songs (frontend).       | Yes            |
| GET  | `/song/dummyData` | Add some dummy data (debugging).       | Yes            |

The Library service uses the Playlist services hidden endpoint to update partial songs in case a song's information changes.

| Mapping | Endpoint              | Uses    | Mapping | Endpoint |
| ------- | ---------------------- | ------- | -------- | ------------- |
| PUT     | `/song/{songId}`       | Uses       | PUT      | `/playlist/updatePartialSongs` | Special non-public method used by the library to update partial song data if a song were to get updated | Not accessible |

The Library service uses the Rating service to retrieve the average rating when returning a song's information.

| Mapping | Endpoint          | Uses   | Mapping | Endpoint |
| ------- | ------------------ | ------ | -------- | ------------- |
| GET     | `/song/{songId}`    | Uses      | GET     | `/rating/{songId}/average-rating` | When adding a song to a playlist, the service will ask the library service about its existence and some details about it.

### Playlist service

This service manages user playlists, allowing users to create, update, and delete playlists, and add or remove songs from playlists.

| Mapping | Endpoints                         | Comments                   | Requires Auth |
| --------- | --------------------------------- | -------------------------- | ------------------- |
| POST      | `/playlist`                       | Create a new playlist.     | Yes |
| POST      | `/playlist/{id}`                       | Create my favorites playlist.     | Yes |
| GET       | `/playlist/{playlistId}`          | Get a playlist.            | No |
| GET       | `/playlist/user/{userId}`         | Get a user's playlists.    | Yes |
| PUT       | `/playlist/{playlistId}`          | Update playlist info.      | Yes |
| PUT       | `/playlist/{playlistId}/{songId}` | Add a song to playlist.    | Yes |
| DELETE    | `/playlist/{playlistId}/{songId}` | Remove song from playlist. | Yes |
| DELETE    | `/playlist/{playlistId}`          | Delete playlist.           | Yes |


The playlist service uses the library services get songs method when adding a song to a playlist to check if it exists and to get its information

| Mapping | Endpoint                     | Uses | Mapping | Endpoint |
| ------- | ----------------------------- | ------- | -------- | ------------- |
| PUT     | `/playlist/{playlistId}/{songId}` | Uses       | PUT     | `/song/{songId}` | Special non-public method used by the library to update partial song data if a song were to get  


### Rating service

This service manages user ratings for songs.

| Mapping | Endpoints          | Comments                              | Requires Auth |
| ------- | ------------------ | ------------------------------------- | ------------- |
| POST    | - `/rating`        | Rate a song.       | Yes            |
| GET     | - `/rating/{itemId}` | Retrieve ratings for a specific item. | Yes            |
| PUT     | - `/rating/{itemId}` | Update a rating for an item.          | Yes            |
| DELETE  | - `/rating/{itemId}` | Delete a rating for an item.          | Yes            |
| GET  | `/rating/all` | Get all ratings (debugging).       | Yes            |


## Postman tests

![Postman tests](PostmanSummary.jpg)

## My expansions

1. Zet de deployment docker-compose.yml om naar Kubernetes Manifest .yml-files (+5%)
2. Gebruik ClusterIP & Nodeport op een logische manier (+5%)
3. Maak een front-end voor je applicatie die niet gehost is op Okteto. (+15%)
4. Mijn project bevat 4 services i.p.v. 3
