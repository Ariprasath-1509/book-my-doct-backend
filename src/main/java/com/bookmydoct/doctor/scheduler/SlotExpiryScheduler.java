package com.bookmydoct.doctor.scheduler;

import com.bookmydoct.doctor.data.entity.Slot;
import com.bookmydoct.doctor.data.enums.SlotStatus;
import com.bookmydoct.doctor.repository.SlotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SlotExpiryScheduler {

    private final SlotRepository slotRepository;

    /**
     * Runs every minute.
     * Marks AVAILABLE slots as EXPIRED
     * if their end time has passed.
     */
    @Scheduled(fixedRate = 60000)
    @Transactional
    public void expireSlots() {

        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        List<Slot> slotsToExpire =
                slotRepository.findSlotsToExpire(
                        today,
                        now);

        if (slotsToExpire.isEmpty()) {
            return;
        }

        slotsToExpire.forEach(
                slot -> slot.setStatus(SlotStatus.EXPIRED));

        slotRepository.saveAll(slotsToExpire);
        log.info(
                "{} slots marked as EXPIRED",
                slotsToExpire.size());
    }

//    end of the day cleanup schedular
    @Scheduled(cron = "0 0 1 * * *")
    @Transactional
    public void deleteExpiredSlots() {

        long deletedCount =
                slotRepository.deleteExpiredSlots(LocalDate.now());

        log.info("{} expired slots deleted", deletedCount);
    }
}