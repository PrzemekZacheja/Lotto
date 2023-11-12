package pl.lotto.domain.resultchecker;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import pl.lotto.domain.numbersgenerator.dto.WinnerNumbersDto;
import pl.lotto.domain.numbersreceiver.dto.TicketDto;
import pl.lotto.domain.resultchecker.dto.ResultDto;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Log4j2
class ResultChecker {

    public static final int HITS_TO_WIN = 3;
    private final ResultRepository repository;

    private static List<ResultDto> generateResult(List<TicketDto> ticketDtoToCheck, WinnerNumbersDto winnerNumbersDto) {
        List<ResultDto> listToReturn = new ArrayList<>();
        for (TicketDto singleTicketDtoToCheck : ticketDtoToCheck) {
            int result = intersectionOfTwoLists(winnerNumbersDto, singleTicketDtoToCheck);
            boolean isWinner = isWinner(result);

            ResultDto resultOfResultCheckerDto = ResultDto.builder()
                                                          .isWinner(isWinner)
                                                          .winnersNumbers(winnerNumbersDto.winningNumbers())
                                                          .ticketId(singleTicketDtoToCheck.ticketId())
                                                          .drawDate(singleTicketDtoToCheck.drawDate())
                                                          .numbersFromUser(singleTicketDtoToCheck.userNumbers())
                                                          .message("Ticket checked correctly")
                                                          .build();
            listToReturn.add(resultOfResultCheckerDto);
        }
        return listToReturn;
    }

    private static int intersectionOfTwoLists(WinnerNumbersDto winnerNumbersDto, TicketDto ticketDtoToChange) {
        List<Integer> setToRetain = new ArrayList<>(ticketDtoToChange.userNumbers());
        setToRetain.retainAll(winnerNumbersDto.winningNumbers());
        return setToRetain.size();
    }

    private static boolean isWinner(int result) {
        return result >= HITS_TO_WIN;
    }

    List<ResultDto> checkWinnerResults(List<TicketDto> ticketDtoToCheck, WinnerNumbersDto winnerNumbersDto) {
        if (winnerNumbersDto == null || winnerNumbersDto.winningNumbers()
                                                        .isEmpty()) {
            return List.of(ResultDto.builder()
                                    .message("Ticket checked incorrectly")
                                    .isWinner(false)
                                    .build());
        }

        List<ResultDto> resultDtos = generateResult(ticketDtoToCheck, winnerNumbersDto);
        List<Result> entities = ResultCheckerMapper.mapToResult(resultDtos);
        repository.saveAll(entities);
        log.info("Saved to repository : " + resultDtos.size() + " elements");
        return resultDtos;
    }
}