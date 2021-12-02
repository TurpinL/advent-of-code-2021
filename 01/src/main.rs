use std::fs;
use itertools::Itertools;

fn main() {
    println!("Part 1");
    part1();

    println!("\nPart 2");
    part2();
}

fn part1() {
    let input = fs::read_to_string("input")
        .expect("Something went wrong reading the file");

    let input_lines = input.split_whitespace();
    let input_numbers = input_lines.map(|x| x.parse::<i32>().unwrap());

    let mut count = 0;

    for (prev_num, next_num) in input_numbers.tuple_windows() {
        // println!("{}, {}: {}", prev_num, next_num, prev_num < next_num);

        if prev_num < next_num {
            count += 1;
        }
    }

    println!("{}", count)
}

fn part2() {
    let input = fs::read_to_string("input")
        .expect("Something went wrong reading the file");

    let input_lines = input.split_whitespace();
    let input_numbers = input_lines.map(|x| x.parse::<i32>().unwrap());
    let three_point_moving_avg = input_numbers.tuple_windows::<(_, _, _)>().map(|(a, b, c)| a + b + c);

    let mut count = 0;

    for (prev_sum, next_sum) in three_point_moving_avg.tuple_windows() {
        // println!("{}, {}: {}", prev_sum, next_sum, prev_sum < next_sum);

        if prev_sum < next_sum {
            count += 1;
        }
    }

    println!("{}", count)
}
