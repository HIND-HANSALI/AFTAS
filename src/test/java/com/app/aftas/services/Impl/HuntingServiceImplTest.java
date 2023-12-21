//package com.app.aftas.services.Impl;
//
//import com.app.aftas.handlers.exception.ResourceNotFoundException;
//import com.app.aftas.models.*;
//import com.app.aftas.repositories.HuntingRepository;
//import com.app.aftas.services.CompetitionService;
//import com.app.aftas.services.FishService;
//import com.app.aftas.services.MemberService;
//import com.app.aftas.services.RankingService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//class HuntingServiceImplTest {
//    @Mock
//    private CompetitionService competitionService;
//
//    @Mock
//    private MemberService memberService;
//
//    @Mock
//    private FishService fishService;
//
//    @Mock
//    private RankingService rankingService;
//
//    @Mock
//    private HuntingRepository huntingRepository;
//
//    @InjectMocks
//    private HuntingServiceImpl huntingService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void addHuntingResult() {
//        // Setup test data
//        Hunting hunting = new Hunting();
//        Competition competition = new Competition();
//        Member member = new Member();
//        Fish fish = new Fish();
//        Ranking ranking = new Ranking();
//
//        hunting.setCompetition(competition);
//        hunting.setMember(member);
//        hunting.setFish(fish);
//
//        // Mocking behavior
//        when(competitionService.getCompetitionById(anyLong())).thenThrow(new ResourceNotFoundException);
////        when(memberService.getMemberById(anyLong())).thenReturn(member);
////        when(fishService.getFishById(anyLong())).thenReturn(fish);
////        when(rankingService.getRankingsByMemberIdAndCompetitionId(anyLong(), anyLong())).thenReturn(ranking);
////        when(huntingRepository.findByCompetitionIdAndMemberIdAndFishId(anyLong(), anyLong(), anyLong())).thenReturn(null);
////        when(fish.getLevel()).thenReturn(Level.builder().build());
////        when(huntingRepository.save(any())).thenReturn(hunting);
//
//        // Call the method
//        Hunting result = huntingService.addHuntingResult(hunting);
//
//        // Verify the interactions
////        verify(competitionService).getCompetitionById(anyLong());
////        verify(memberService).getMemberById(anyLong());
////        verify(fishService).getFishById(anyLong());
////        verify(rankingService).getRankingsByMemberIdAndCompetitionId(anyLong(), anyLong());
////        verify(huntingRepository).findByCompetitionIdAndMemberIdAndFishId(anyLong(), anyLong(), anyLong());
////        verify(huntingRepository).save(any());
//
//        // Assertions
////        assertNotNull(result);
//        // Add more assertions based on your business logic
//        assertThrows(ResourceNotFoundException.class ,()->{
//                huntingService.addHuntingResult(result);
//        });
//    }
//    }
