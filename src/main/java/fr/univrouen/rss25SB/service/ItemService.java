package fr.univrouen.rss25SB.service;

import org.springframework.stereotype.Service;

import fr.univrouen.rss25SB.model.ItemSummaryDTO;
import fr.univrouen.rss25SB.repository.ItemRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<ItemSummaryDTO> getAllItemSummaries() {
        return itemRepository.findAll().stream()
            .map(item -> new ItemSummaryDTO(item.getId(), item.getPublished(), item.getGuid()))
            .collect(Collectors.toList());
    }
}

