package pl.lotto.domain.result_checker;

import lombok.AllArgsConstructor;
import pl.lotto.domain.numbers_generator.dto.WinnerNumbersDto;
import pl.lotto.domain.numbers_receiver.dto.TicketDto;
import pl.lotto.domain.result_checker.dto.TicketCheckedDto;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
class ResultChecker {


    private final TicketCheckedRepository repository;

    List<TicketCheckedDto> checkWinnerResults(List<TicketDto> ticketDtos, WinnerNumbersDto winnerNumbersDto) {
        List<TicketCheckedDto> listToReturn = new ArrayList<>();

        if (winnerNumbersDto == null || winnerNumbersDto.winningNumbers().isEmpty()){
            return List.of(TicketCheckedDto.builder()
                    .message("Ticket checked incorrectly")
                    .isWinner(false).build());
        }

        for (TicketDto ticketDto : ticketDtos) {
            int result = intersectionOfTwoLists(winnerNumbersDto, ticketDto);
            boolean isWinner = isWinner(result);

            TicketCheckedDto resultOfResultCheckerDto = TicketCheckedDto.builder()
                    .isWinner(isWinner)
                    .winnersNumbers(winnerNumbersDto.winningNumbers())
                    .ticketDto(ticketDto)
                    .message("Ticket checked correctly")
                    .build();
            listToReturn.add(resultOfResultCheckerDto);

            repository.saveAll(ResultCheckerMapper.mapToTicketChecked(listToReturn));
        }
        return listToReturn;
    }

    private static boolean isWinner( int result) {
        return result >= 3;
    }

    private static int intersectionOfTwoLists(WinnerNumbersDto winnerNumbersDto,  TicketDto ticketDto) {
        List<Integer> setToRetain = new ArrayList<>(ticketDto.numbersFromUser());
        setToRetain.retainAll(winnerNumbersDto.winningNumbers());
        return setToRetain.size();
    }
}