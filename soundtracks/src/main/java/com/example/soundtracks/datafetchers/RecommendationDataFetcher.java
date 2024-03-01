package com.example.soundtracks.datafetchers;

import java.util.Map;
import java.util.List;
import com.example.soundtracks.generated.types.Playlist;
import com.example.soundtracks.generated.types.Recipe;
import com.example.soundtracks.models.MappedPlaylist;
import com.example.soundtracks.models.PlaylistCollection;
import com.example.soundtracks.datasources.SpotifyClient;
import org.springframework.beans.factory.annotation.Autowired;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsEntityFetcher;

@DgsComponent
public class RecommendationDataFetcher {

    private final SpotifyClient spotifyClient;

    @Autowired
    public RecommendationDataFetcher(SpotifyClient spotifyClient) {
        this.spotifyClient = spotifyClient;
    }

    @DgsEntityFetcher(name="Recipe")
    public Recipe recommendedPlaylists(Map<String, String> representation) {

        System.out.println(representation);

        Recipe recipe = new Recipe();

        String name = representation.get("name");
        PlaylistCollection response = spotifyClient.search(name);
        List<MappedPlaylist> playlists = response.getPlaylists();
        List<Playlist> preparedPlaylists = playlists.stream().map(MappedPlaylist::getPlaylist).toList();
        recipe.setRecommendedPlaylists(preparedPlaylists);
        return recipe;
    }


}

