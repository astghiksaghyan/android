import React, { Component } from 'react';

class DailyStatus extends Component {
    init = function() {
      console.log('click !!!');
    };

    render() {
        return (
            <div className="App" onClick={this.init}>
              <p> Daily Status </p>
            </div>
        );
    }
}

export default DailyStatus;
