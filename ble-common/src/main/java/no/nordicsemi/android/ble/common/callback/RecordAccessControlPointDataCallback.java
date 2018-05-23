package no.nordicsemi.android.ble.common.callback;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.support.annotation.NonNull;

import no.nordicsemi.android.ble.callback.profile.ProfileReadResponse;
import no.nordicsemi.android.ble.common.profile.RecordAccessControlPointCallback;
import no.nordicsemi.android.ble.data.Data;

/**
 * Record Access Control Point callback that parses received data.
 * If the value does match characteristic specification the
 * {@link #onInvalidDataReceived(BluetoothDevice, Data)} callback will be called.
 * See: https://www.bluetooth.com/specifications/gatt/viewer?attributeXmlFile=org.bluetooth.characteristic.record_access_control_point.xml
 */
@SuppressWarnings({"ConstantConditions", "WeakerAccess"})
public abstract class RecordAccessControlPointDataCallback extends ProfileReadResponse implements RecordAccessControlPointCallback {
	private final static int OP_CODE_NUMBER_OF_STORED_RECORDS_RESPONSE = 5;
	private final static int OP_CODE_RESPONSE_CODE = 6;
	private final static int OPERATOR_NULL = 0;
	private final static int RACP_RESPONSE_SUCCESS = 1;
	private final static int RACP_ERROR_NO_RECORDS_FOUND = 6;

	public RecordAccessControlPointDataCallback() {
		// empty
	}

	protected RecordAccessControlPointDataCallback(final Parcel in) {
		super(in);
	}

	@Override
	public void onDataReceived(@NonNull final BluetoothDevice device, @NonNull final Data data) {
		super.onDataReceived(device, data);

		if (data.size() < 3) {
			onInvalidDataReceived(device, data);
			return;
		}

		final int opCode = data.getIntValue(Data.FORMAT_UINT8, 0);
		if (opCode != OP_CODE_NUMBER_OF_STORED_RECORDS_RESPONSE && opCode != OP_CODE_RESPONSE_CODE) {
			onInvalidDataReceived(device, data);
			return;
		}

		final int operator = data.getIntValue(Data.FORMAT_UINT8, 1);
		if (operator != OPERATOR_NULL) {
			onInvalidDataReceived(device, data);
			return;
		}

		switch (opCode) {
			case OP_CODE_NUMBER_OF_STORED_RECORDS_RESPONSE: {
				// Field size is defined per service
				int numberOfRecords;

				switch (data.size() - 2) {
					case 1:
						numberOfRecords = data.getIntValue(Data.FORMAT_UINT8, 2);
						break;
					case 2:
						numberOfRecords = data.getIntValue(Data.FORMAT_UINT16, 2);
						break;
					case 4:
						numberOfRecords = data.getIntValue(Data.FORMAT_UINT32, 2);
						break;
					default:
						// Other field sizes are not supported
						onInvalidDataReceived(device, data);
						return;
				}
				onNumberOfRecordsReceived(device, numberOfRecords);
				break;
			}
			case OP_CODE_RESPONSE_CODE: {
				if (data.size() != 4) {
					onInvalidDataReceived(device, data);
					return;
				}

				final int requestCode = data.getIntValue(Data.FORMAT_UINT8, 2);
				final int responseCode = data.getIntValue(Data.FORMAT_UINT8, 3);
				if (responseCode == RACP_RESPONSE_SUCCESS) {
					onRecordAccessOperationCompleted(device, requestCode);
				} else if (responseCode == RACP_ERROR_NO_RECORDS_FOUND) {
					onRecordAccessOperationCompletedWithNoRecordsFound(device, requestCode);
				} else {
					onRecordAccessOperationError(device, requestCode, responseCode);
				}
				break;
			}
		}
	}
}
