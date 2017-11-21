package lucene.sent.cache.index;

public enum SENT_CACHE_COLUMN {
	
	// The sequence of columns should be maintained intact.
	
	ACCOUNT_ID("account_id"),
	LAUNCH_KEY("launch_key"),
	//RIID("riid"),
	SENT_DATE_KEY("sent_date_key"),
	ID("id"),
	DOMAIN_KEY("domain_key"),
	MEDIA_KEY("media_key"),
	SEGMENT_KEY("segment_key"),
	OS_KEY("os_key"),
	DS_KEY("ds_key"),
	SS_KEY("ss_key"),
	VMTA_IP_KEY("vmta_ip_key"),
	ENACTMENT_ID("enactment_id"),
	//PERSONALIZATION_DT("personalization_dt"),
	CAMPAIGN_VERSION_TEST_KEY("campaign_version_test_key");
	
	private final String value;
	
	SENT_CACHE_COLUMN(String v) {
        value = v;
    }
	
    public String value() {
        return value;
    }

    @Override
    public String toString() {
    	return value;
    }
	
	
}

