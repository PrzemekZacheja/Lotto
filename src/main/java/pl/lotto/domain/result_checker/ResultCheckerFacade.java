package pl.lotto.domain.result_checker;

import lombok.AllArgsConstructor;
import pl.lotto.domain.drawdategenerator.DrawDateFacade;
import pl.lotto.domain.numbers_generator.NumbersGeneratorFacade;
import pl.lotto.domain.numbers_generator.dto.WinnerNumbersDto;
import pl.lotto.domain.numbers_receiver.NumberReceiverFacade;
import pl.lotto.domain.numbers_receiver.dto.TicketDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class ResultCheckerFacade {

    private final NumbersGeneratorFacade numbersGenerator;
    private final NumberReceiverFacade numberReceiverFacade;
    private final DrawDateFacade drawDateFacade;


    List<ResultOfResultCheckerDto> generateWinningTickets() {
        LocalDateTime dateOfNextDraw = drawDateFacade.getDateOfNextDraw();

        return checkWinnerResults(numberReceiverFacade.retrieveAllTicketsByNextDrawDate(dateOfNextDraw),
                numbersGenerator.retrieveAllWinnerNumbersByNextDrawDate(dateOfNextDraw));
    }

    private List<ResultOfResultCheckerDto> checkWinnerResults(List<TicketDto> ticketDtos, WinnerNumbersDto winnerNumbersDto) {
        List<ResultOfResultCheckerDto> listToReturn = new ArrayList<>();

        for (TicketDto ticketDto : ticketDtos) {
            List<Integer> setToRetain = new ArrayList<>(ticketDto.numbersFromUser());
            setToRetain.retainAll(winnerNumbersDto.winningNumbers());
            int result = setToRetain.size();
            boolean isWinner = result >= 3;

            ResultOfResultCheckerDto resultOfResultCheckerDto = ResultOfResultCheckerDto.builder()
                    .isWinner(isWinner)
                    .winnersNumbers(winnerNumbersDto.winningNumbers())
                    .ticketDto(ticketDto)
                    .build();
            listToReturn.add(resultOfResultCheckerDto);
        }
        return listToReturn;
    }
}