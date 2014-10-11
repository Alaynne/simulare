package br.simulare.business.ta.candlestick.reversalpattern;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import br.simulare.business.ta.candlestick.bearishreversalpattern.*;
import br.simulare.business.ta.candlestick.bullishreversalpattern.*;
import br.simulare.util.ConfigurationManager;

/**
 * It factories types of reversal candlestick patterns. It implements Factory design
 * pattern.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public class ReversalCandlePatternTypeFactory {
	
	// Logger for this class
	private static final Logger logger = Logger.
			getLogger(ReversalCandlePatternTypeFactory.class);
	
	private double littleShadowParameter;
	private double bigShadowParameter;
	private double volumeIncreaseParameter;
		
	private final double DEFAULT_LITTLE_SHADOW_PARAMETER = 0.2;
	private final double DEFAULT_BIG_SHADOW_PARAMETER = 2.0;
	private final double DEFAULT_VOLUME_INCREASE_PARAMETER = 1.2;
	
	public ReversalCandlePatternTypeFactory() {
		
		try {
			littleShadowParameter = Double.
				parseDouble(ConfigurationManager.getInstance().
					getValue("reversalCandlePatterns.littleShadowParameter"));
			bigShadowParameter = Double.
				parseDouble(ConfigurationManager.getInstance().
				getValue("reversalCandlePatterns.bigShadowParameter"));
			volumeIncreaseParameter = Double.
				parseDouble(ConfigurationManager.getInstance().
					getValue("reversalCandlePatterns.volumeIncreaseParameter"));
		} catch (Exception e) {
			littleShadowParameter = DEFAULT_LITTLE_SHADOW_PARAMETER;
			bigShadowParameter =DEFAULT_BIG_SHADOW_PARAMETER;
			volumeIncreaseParameter = DEFAULT_VOLUME_INCREASE_PARAMETER;
			
			if (logger.isInfoEnabled()) {
				logger.info("ReversalCandlePatternTypeFactory() - Using " +
						"default configurations.");
			}
		}
		
	}
	
	public static List<String> getAllBullishCandlePatternTypes() {

		List<String> bullishCandlePatternTypes = new ArrayList<String>();
		
		bullishCandlePatternTypes.add(BullishHomingPigeon.getName());
		bullishCandlePatternTypes.add(BullishAbandonedBaby.getName());
		bullishCandlePatternTypes.add(BullishKicking.getName());
		bullishCandlePatternTypes.add(BullishEngulfing.getName());
		bullishCandlePatternTypes.add(MorningStar.getName());
		bullishCandlePatternTypes.add(BullishHarami.getName());
		bullishCandlePatternTypes.add(Hammer.getName());
		bullishCandlePatternTypes.add(InvertedHammer.getName());
		bullishCandlePatternTypes.add(PiercingLine.getName());
		bullishCandlePatternTypes.add(BottomTweezers.getName());
		
		return bullishCandlePatternTypes;
	
	}

	public static List<String> getAllBearishCandlePatternTypes() {
		
		List<String> bearishCandlePatternTypes = new ArrayList<String>();
		
		bearishCandlePatternTypes.add(BearishAbandonedBaby.getName());
		bearishCandlePatternTypes.add(BearishKicking.getName());
		bearishCandlePatternTypes.add(HangingMan.getName());
		bearishCandlePatternTypes.add(BearishEngulfing.getName());
		bearishCandlePatternTypes.add(ShootingStar.getName());
		bearishCandlePatternTypes.add(EveningStar.getName());
		bearishCandlePatternTypes.add(BearishHarami.getName());
		bearishCandlePatternTypes.add(DarkCloudCover.getName());
		bearishCandlePatternTypes.add(TopTweezers.getName());
		
		return bearishCandlePatternTypes;
		
	}

	public static boolean isBullishPatternTypeValid(String patternType) {
		return (getAllBullishCandlePatternTypes().contains(patternType));
	}
	
	public static boolean isBearishPatternTypeValid(String patternType) {
		return (getAllBearishCandlePatternTypes().contains(patternType));
	}
	
	// It is a factory method.
	public BullishReversalCandlePattern buildBullishPattern(String patternType) {
	
		BullishReversalCandlePattern bullishPattern = null;
		
		if (BullishHomingPigeon.getName().equals(patternType)) {
			bullishPattern = new BullishHomingPigeon();
		} else if (BullishAbandonedBaby.getName().equals(patternType)) {
			bullishPattern = new BullishAbandonedBaby();
		} else if (BullishKicking.getName().equals(patternType)) {
			bullishPattern = new BullishKicking(littleShadowParameter, 
					volumeIncreaseParameter);
		} else if (BullishEngulfing.getName().equals(patternType)) {
			bullishPattern = new BullishEngulfing(volumeIncreaseParameter);
		} else if (MorningStar.getName().equals(patternType)) {
			bullishPattern = new MorningStar();
		} else if (BullishHarami.getName().equals(patternType)) {
			bullishPattern = new BullishHarami();
		} else if (Hammer.getName().equals(patternType)) {
			bullishPattern = new Hammer(littleShadowParameter, 
					bigShadowParameter, volumeIncreaseParameter);
		} else if (InvertedHammer.getName().equals(patternType)) {
			bullishPattern = new InvertedHammer(littleShadowParameter, 
					bigShadowParameter);
		} else if (PiercingLine.getName().equals(patternType)) {
			bullishPattern = new PiercingLine();
		} else if (BottomTweezers.getName().equals(patternType)) {
			bullishPattern = new BottomTweezers();
		}
		
		return bullishPattern;
		
	}
	
	// It is a factory method.
	public BearishReversalCandlePattern buildBearishPattern(String patternType){
		
		BearishReversalCandlePattern bearishPattern = null;
		
		if (BearishAbandonedBaby.getName().equals(patternType)) {
			bearishPattern = new BearishAbandonedBaby();
		} else if (BearishKicking.getName().equals(patternType)) {
			bearishPattern = new BearishKicking(littleShadowParameter, 
					volumeIncreaseParameter);
		} else if (HangingMan.getName().equals(patternType)) {
			bearishPattern = new HangingMan(littleShadowParameter,
					bigShadowParameter, volumeIncreaseParameter);
		} else if (BearishEngulfing.getName().equals(patternType)) {
			bearishPattern = new BearishEngulfing(volumeIncreaseParameter);
		} else if (ShootingStar.getName().equals(patternType)) {
			bearishPattern = new ShootingStar(littleShadowParameter,
					bigShadowParameter, volumeIncreaseParameter);
		} else if (EveningStar.getName().equals(patternType)) {
			bearishPattern = new EveningStar();
		} else if (BearishHarami.getName().equals(patternType)) {
			bearishPattern = new BearishHarami();
		} else if (DarkCloudCover.getName().equals(patternType)) {
			bearishPattern = new DarkCloudCover();
		} else if (TopTweezers.getName().equals(patternType)) {
			bearishPattern = new TopTweezers();
		}	
			
		return bearishPattern;
		
	}

	// It is a factory method.
	public ReversalCandlePatternType buildReversalPatternType(String patternType){
		
		ReversalCandlePatternType reversalCandlePatternType;
		
		if ((reversalCandlePatternType = buildBullishPattern(patternType)) != null) {
			return reversalCandlePatternType;
		} else if ((reversalCandlePatternType = buildBearishPattern(patternType)) != null){
			return reversalCandlePatternType;
		}
			
		return null;
		
	}
	
	public String getPatternShortName(String patternType) {
		
		ReversalCandlePatternType pattern;
		
		if ((pattern = buildBullishPattern(patternType)) != null) {
			return pattern.getShortName();
		} else if ((pattern = buildBearishPattern(patternType)) != null) {
			return pattern.getShortName();
		}
		
		return "";
		
	}
	
}
