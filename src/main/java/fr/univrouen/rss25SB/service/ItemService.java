package fr.univrouen.rss25SB.service;

import org.springframework.stereotype.Service;

import fr.univrouen.rss25SB.model.ApiResponse;
import fr.univrouen.rss25SB.model.Feed;
import fr.univrouen.rss25SB.model.Item;
import fr.univrouen.rss25SB.model.ItemSummaryDTO;
import fr.univrouen.rss25SB.repository.ItemRepository;
import fr.univrouen.rss25SB.repository.FeedRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final FeedRepository feedRepository;

    public ItemService(ItemRepository itemRepository, FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
        this.itemRepository = itemRepository;
    }

    public List<ItemSummaryDTO> getAllItemSummaries() {
        return itemRepository.findAll().stream()
            .map(item -> {
                LocalDateTime date = item.getPublished() != null ? item.getPublished() : item.getUpdated();
                return new ItemSummaryDTO(item.getId(), date, item.getGuid());
            })
            .collect(Collectors.toList());
    }

    public Optional<Item> getItemById(Long id) {
        return itemRepository.findById(id);
    }

    public ApiResponse deleteItemById(Long id) {
    Optional<Item> itemOpt = itemRepository.findById(id);

    if (itemOpt.isPresent()) {
        Item item = itemOpt.get();
        Feed feed = item.getFeed();

        itemRepository.delete(item);

        // Si le feed existe encore et n'a plus d'items, on le supprime
        if (feed != null && feed.getItems().isEmpty()) {
            feedRepository.delete(feed);
        }

        return new ApiResponse(id, "DELETED");
    } else {
        return new ApiResponse("ERROR");
    }
}
}

