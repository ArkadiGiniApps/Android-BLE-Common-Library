package no.nordicsemi.android.ble.common.profile.glucose;

import android.bluetooth.BluetoothDevice;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

@SuppressWarnings("unused")
public interface GlucoseMeasurementContextCallback {
	int UNIT_mg = 0;
	int UNIT_ml = 1;

	enum Carbohydrate {
		RESERVED(0),
		BREAKFAST(1),
		LUNCH(2),
		DINNER(3),
		SNACK(4),
		DRINK(5),
		SUPPER(6),
		BRUNCH(7);

		public byte value;

		Carbohydrate(final int code) {
			this.value = (byte) code;
		}

		static Carbohydrate from(final int code) {
			switch (code) {
				case 1:
					return BREAKFAST;
				case 2:
					return LUNCH;
				case 3:
					return DINNER;
				case 4:
					return SNACK;
				case 5:
					return DRINK;
				case 6:
					return SUPPER;
				case 7:
					return BRUNCH;
				default:
					return RESERVED;
			}
		}
	}

	enum Meal {
		RESERVED(0),
		PREPRANDIAL(1),
		POSTPRANDIAL(2),
		FASTING(3),
		CASUAL(4),
		BEDTIME(5);

		public byte value;

		Meal(final int code) {
			this.value = (byte) code;
		}

		static Meal from(final int code) {
			switch (code) {
				case 1:
					return PREPRANDIAL;
				case 2:
					return POSTPRANDIAL;
				case 3:
					return FASTING;
				case 4:
					return CASUAL;
				case 5:
					return BEDTIME;
				default:
					return RESERVED;
			}
		}
	}

	enum Tester {
		RESERVED(0),
		SELF(1),
		HEALTH_CARE_PROFESSIONAL(2),
		LAB_TEST(3),
		NOT_AVAILABLE(15);

		public byte value;

		Tester(final int code) {
			this.value = (byte) code;
		}

		static Tester from(final int code) {
			switch (code) {
				case 1:
					return SELF;
				case 2:
					return HEALTH_CARE_PROFESSIONAL;
				case 3:
					return LAB_TEST;
				case 15:
					return NOT_AVAILABLE;
				default:
					return RESERVED;
			}
		}
	}

	enum Health {
		RESERVED(0),
		MINOR_HEALTH_ISSUES(1),
		MAJOR_HEALTH_ISSUES(2),
		DURING_MENSES(3),
		UNDER_STRESS(4),
		NO_HEALTH_ISSUES(5),
		NOT_AVAILABLE(15);

		public byte value;

		Health(final int code) {
			this.value = (byte) code;
		}

		static Health from(final int code) {
			switch (code) {
				case 1:
					return MINOR_HEALTH_ISSUES;
				case 2:
					return MAJOR_HEALTH_ISSUES;
				case 3:
					return DURING_MENSES;
				case 4:
					return UNDER_STRESS;
				case 5:
					return NO_HEALTH_ISSUES;
				case 15:
					return NOT_AVAILABLE;
				default:
					return RESERVED;
			}
		}
	}

	enum Medication {
		RESERVED(0),
		RAPID_ACTING_INSULIN(1),
		SHORT_ACTING_INSULIN(2),
		INTERMEDIATE_ACTING_INSULIN(3),
		LONG_ACTING_INSULIN(4),
		PRE_MIXED_INSULIN(5);

		public byte value;

		Medication(final int code) {
			this.value = (byte) code;
		}

		static Medication from(final int code) {
			switch (code) {
				case 1:
					return RAPID_ACTING_INSULIN;
				case 2:
					return SHORT_ACTING_INSULIN;
				case 3:
					return INTERMEDIATE_ACTING_INSULIN;
				case 4:
					return LONG_ACTING_INSULIN;
				case 5:
					return PRE_MIXED_INSULIN;
				default:
					return RESERVED;
			}
		}
	}

	/**
	 * Callback called when Glucose Measurement Context value was received.
	 *
	 * @param device             target device.
	 * @param sequenceNumber     the sequence number that matches the Glucose Measurement sequence number.
	 * @param carbohydrate       optional carbohydrate ID.
	 * @param carbohydrateAmount amount of carbohydrate in grams.
	 * @param meal               optional meal ID.
	 * @param tester             optional tester ID.
	 * @param health             optional health information.
	 * @param exerciseDuration   exercise duration in seconds. Value 65535 means an overrun.
	 * @param exerciseIntensity  exercise intensity in percent.
	 * @param medication         optional medication ID.
	 * @param medicationAmount   amount of medication in milligrams or milliliters, depending on the medicationUnit value.
	 * @param medicationUnit     the unit of medication amount. One of {@link #UNIT_mg} of {@link #UNIT_ml}.
	 * @param HbA1c              amount of glycated haemoglobin in percentage.
	 */
	void onGlucoseMeasurementContextReceived(final @NonNull BluetoothDevice device, final int sequenceNumber,
											 final @Nullable Carbohydrate carbohydrate, final @Nullable Float carbohydrateAmount,
											 final @Nullable Meal meal,
											 final @Nullable Tester tester, final @Nullable Health health,
											 final @Nullable Integer exerciseDuration, final @Nullable Integer exerciseIntensity,
											 final @Nullable Medication medication, final @Nullable Float medicationAmount, final @Nullable Integer medicationUnit,
											 final @Nullable Float HbA1c);
}
