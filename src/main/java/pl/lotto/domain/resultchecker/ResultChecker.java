package pl.lotto.domain.resultchecker;

import lombok.AllArgsConstructor;
import pl.lotto.domain.numbersgenerator.dto.WinnerNumbersDto;
import pl.lotto.domain.numbersreceiver.dto.TicketDto;
import pl.lotto.domain.resultchecker.dto.TicketCheckedDto;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
class ResultChecker {


    private final TicketCheckedRepository repository;

    private static boolean isWinner(int result) {
        return result >= 3;
    }

    private static int intersectionOfTwoLists(WinnerNumbersDto winnerNumbersDto, TicketDto ticketDtoToChange) {
        List<Integer> setToRetain = new ArrayList<>(ticketDtoToChange.userNumbers());
        setToRetain.retainAll(winnerNumbersDto.winningNumbers());
        return setToRetain.size();
    }

    List<TicketCheckedDto> checkWinnerResults(List<TicketDto> ticketDtoToChanges, WinnerNumbersDto winnerNumbersDto) {
        List<TicketCheckedDto> listToReturn = new ArrayList<>();

        if (winnerNumbersDto == null || winnerNumbersDto.winningNumbers()
                .isEmpty()) {
            return List.of(TicketCheckedDto.builder()
                    .message("Ticket checked incorrectly")
                    .isWinner(false)
                    .build());
        }

        for (TicketDto ticketDtoToChange : ticketDtoToChanges) {
            int result = intersectionOfTwoLists(winnerNumbersDto, ticketDtoToChange);
            boolean isWinner = isWinner(result);

            TicketCheckedDto resultOfResultCheckerDto = TicketCheckedDto.builder()
                    .isWinner(isWinner)
                    .winnersNumbers(winnerNumbersDto.winningNumbers())
                    .ticketId(ticketDtoToChange.ticketId())
                    .drawDate(ticketDtoToChange.drawDate())
                    .numbersFromUser(ticketDtoToChange.userNumbers())
                    .message("Ticket checked correctly")
                    .build();
            listToReturn.add(resultOfResultCheckerDto);

            repository.saveAll(ResultCheckerMapper.mapToTicketChecked(listToReturn));
        }
        return listToReturn;
    }
}