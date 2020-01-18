import { StatisticsService } from './../services/statistics.service';
import { Component, OnInit } from '@angular/core';
import { Statistic } from '../domain/statistic';
import { Chart } from 'chart.js';

@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.css']
})
export class StatisticsComponent implements OnInit {

  isError = false;
  errorMessage: string;

  eventsNumberPerMonthStatistic: Statistic;
  eventsNumberPerMonthChart: Chart;

  categoriesStatistic: Statistic;
  categoriesPieChart: Chart;

  constructor(private statisticsService: StatisticsService) { }

  ngOnInit() {
    this.prepareEventsNumberPerMonthChart();
    this.prepareCategoriesPieChart();
  }

  prepareEventsNumberPerMonthChart() {
    this.statisticsService.getEventsNumberPerMonth().subscribe(data => {
      this.eventsNumberPerMonthStatistic = data;
      console.log(this.eventsNumberPerMonthStatistic);
      // prepareEventsNumberPerMonthChart();
      this.eventsNumberPerMonthChart = new Chart('canvas', {
        type: 'bar',
        data: {
          labels: this.eventsNumberPerMonthStatistic.labels,
          datasets: [
            {
              data: this.eventsNumberPerMonthStatistic.values,
              borderColor: '#364D57',
              backgroundColor: '#0000FF',

              fill: true
            }
          ]
        },
        options: {
          responsive: true,
          legend: {
            display: false
          },
          scales: {
            xAxes: [{
              display: true,
              ticks: {
                fontSize: 15
              }
            }],
            yAxes: [{
              display: true,
              ticks: {
                beginAtZero: true,
                fontSize: 15,
                stepSize: 1
              }
            }],
          }
        }
      });
    }, error => {
      this.isError = true;
      this.errorMessage = error.error.message;
    });
  }

  prepareCategoriesPieChart() {
    this.statisticsService.getCategories().subscribe(data => {
      this.categoriesStatistic = data;
      console.log(this.categoriesStatistic);
      // prepareEventsNumberPerMonthChart();
      this.categoriesPieChart = new Chart('categories-pie', {
        type: 'pie',
        data: {
          labels: this.categoriesStatistic.labels,
          datasets: [
            {
              data: this.categoriesStatistic.values,
              borderColor: '#364D57',
              backgroundColor: ['#0000FF',
                '#3cb371',
                '#9966FF',
                '#4C4CFF',
                '#00FFFF',
                '#f990a7',
                '#aad2ed',
                '#FF00FF',
                'Blue',
                'Red',
                'Blue'],
              fill: true
            }
          ]
        },
        options: {
          responsive: true,
          legend: {
            display: true
          }
        }
      });
    }, error => {
      this.isError = true;
      this.errorMessage = error.error.message;
    });
  }

}
