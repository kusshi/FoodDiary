package com.kusshi.springboot;

public enum State {
	
	INITIAL {
		@Override
		public State record() {
			return START_RECORDIG_FOOD;
		}
		
		@Override
		public State browse() {
			return START_BROWSING_RECORD;
		}
		
		@Override 
		public State cancel() {
			return INITIAL;
		}
	},
	
	START_RECORDIG_FOOD {
		@Override
		public State accept() {
			return WAIT_FOOD_NAME;
		}
		
		@Override 
		public State cancel() {
			return INITIAL;
		}
	},

	WAIT_FOOD_NAME {
		@Override
		public State accept() {
			return SET_FOOD_NAME;
		}
		
		@Override 
		public State cancel() {
			return INITIAL;
		}
	},
	
	SET_FOOD_NAME {
		@Override
		public State accept() {
			return WAIT_FOOD_CALORIE;
		}
		
		@Override 
		public State cancel() {
			return INITIAL;
		}
	},
	
	WAIT_FOOD_CALORIE {
		@Override
		public State accept() {
			return SET_FOOD_CALORIE;
		}
		
		@Override 
		public State cancel() {
			return INITIAL;
		}
		
	},
	
	SET_FOOD_CALORIE {
		@Override
		public State accept() {
			return END_RECORDING_FOOD;
		}
		
		@Override 
		public State cancel() {
			return INITIAL;
		}
		
	},
	
	END_RECORDING_FOOD {
		@Override
		public State accept() {
			return INITIAL;
		}
		
		@Override 
		public State cancel() {
			return INITIAL;
		}
		
	},
	
	START_BROWSING_RECORD {
		@Override
		public State accept() {
			return BROWSE_RECORD;
		}
		
		@Override 
		public State cancel() {
			return INITIAL;
		}
		
	},
	
	BROWSE_RECORD {
		@Override
		public State accept() {
			return INITIAL;
		}
		
		@Override 
		public State cancel() {
			return INITIAL;
		}
		
	};

	public State accept() {
		throw new IllegalStateException();
	}
	
	public State cancel() {
		throw new IllegalStateException();
	}
	
	public State record() {
		throw new IllegalStateException();
	}
	public State browse() {
		throw new IllegalStateException();
	}
	


}
