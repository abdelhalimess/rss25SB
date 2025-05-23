package fr.univrouen.rss25SB.service;

import org.springframework.stereotype.Service;

import fr.univrouen.rss25SB.model.Item;
import fr.univrouen.rss25SB.model.ItemSummaryDTO;
import fr.univrouen.rss25SB.repository.ItemRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<ItemSummaryDTO> getAllItemSummaries() {
        return itemRepository.findAll().stream()
<<<<<<< HEAD
            .map(item -> {
                LocalDateTime date = item.getPublished() != null ? item.getPublished() : item.getUpdated();
                return new ItemSummaryDTO(item.getId(), date, item.getGuid());
            })
=======
            .map(item -> new ItemSummaryDTO(item.getId(), item.getPublished(), item.getGuid()))
>>>>>>> 38b053df97a6fdc8dc4b5bb569670d4915b4c034
            .collect(Collectors.toList());
    }

    public Optional<Item> getItemById(Long id) {
        return itemRepository.findById(id);
    }
}

