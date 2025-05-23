package fr.univrouen.rss25SB.service;

import fr.univrouen.rss25SB.model.Feed;
import fr.univrouen.rss25SB.repository.FeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FeedService {

    private final FeedRepository feedRepository;

    @Autowired
    public FeedService(FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    public List<Feed> getAllFeeds() {
        return feedRepository.findAll();
    }

    public Optional<Feed> getFeedById(Long id) {
        return feedRepository.findById(id);
    }

    public boolean existsByTitleAndPubDate(String title, LocalDateTime pubDate) {
        return feedRepository.findByTitleAndPubDate(title, pubDate).isPresent();
    }

    @Transactional
    public Feed saveFeed(Feed feed) {
        return feedRepository.save(feed);
    }

    @Transactional
    public void deleteFeed(Long id) {
        feedRepository.deleteById(id);
    }
}