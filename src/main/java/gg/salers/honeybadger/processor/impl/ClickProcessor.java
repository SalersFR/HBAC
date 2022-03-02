package gg.salers.honeybadger.processor.impl;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.Pair;
import gg.salers.honeybadger.data.PlayerData;
import gg.salers.honeybadger.processor.Processor;
import gg.salers.honeybadger.utils.MathUtils;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ClickProcessor extends Processor {

    private final List<Integer> samples = new ArrayList<>();
    private double deviation, kurtosis, variance, skewness, cps;
    private int ticks, outliers, dupls;

    public ClickProcessor(PlayerData data) {
        super(data);
    }

    @Override
    public void processIn(PacketEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.ARM_ANIMATION) {
            if (samples.size() >= 2) {
                final Pair<List<Double>, List<Double>> pairOutliers = MathUtils.getOutliers(this.samples);
                this.outliers = pairOutliers.getFirst().size() + pairOutliers.getSecond().size();

                this.kurtosis = MathUtils.getKurtosis(samples);
                this.deviation = MathUtils.getStandardDeviation(samples);
                this.variance = MathUtils.getVariance(samples);
                this.skewness = MathUtils.getSkewness(samples);
                this.cps = MathUtils.getCps(samples);

                this.dupls = MathUtils.getDuplicates(samples);
            }


            samples.add(ticks);
            ticks = 0;

            if (samples.size() > 25) {
                samples.subList(0, 10).clear();

            }


        } else if (event.getPacketType() == PacketType.Play.Client.FLYING || event.getPacketType() == PacketType.Play.Client
                .POSITION_LOOK || event.getPacketType() == PacketType.Play.Client.LOOK || event.getPacketType() == PacketType.Play.Client.POSITION) {

            ticks++;
        } else if (event.getPacketType() == PacketType.Play.Client.BLOCK_DIG) {
            if (samples.size() >= 4) {
                samples.subList(0, 3).clear();
            }
        }
    }

    @Override
    public void processOut(PacketEvent event) {

    }
}
